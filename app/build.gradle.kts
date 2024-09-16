plugins {
    alias(libs.plugins.template.android.application)
    alias(libs.plugins.template.android.compose)
    alias(libs.plugins.template.android.application.flavors)
    alias(libs.plugins.template.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.secrets)
    alias(libs.plugins.kotlinx.serialization)
}

val packageName = "com.mitch.template"

android {
    namespace = packageName

    defaultConfig {
        applicationId = packageName
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        androidResources {
            generateLocaleConfig = true
        }
    }
    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    bundle {
        language {
            enableSplit = false
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

detekt {
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    baseline = file("$rootDir/config/detekt/baseline.xml")
    autoCorrect = true
    buildUponDefaultConfig = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        md.required.set(true) // simple Markdown format
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
    propertiesFileName = "secrets.properties"
}

dependencies {
    // Kotlin
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // UI (Compose + Accompanist + Icons + ...)
    implementation(libs.activity.compose)
    implementation(libs.compose.ui.graphics)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)

    // Navigation
    implementation(libs.compose.navigation)

    // Logging
    implementation(libs.timber)

    // Testing
    // Unit
    testImplementation(libs.junit4)
    testImplementation(libs.junit5)
    testRuntimeOnly(libs.junit5.engine)
    testImplementation(libs.junit5.params)
    testImplementation(libs.assertk)
    testImplementation(libs.turbine)
    // Instrumented
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.composeTest.junit4)
    debugImplementation(libs.composeTest.manifest)
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.uiAutomator)

    // Formatting + Linting
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.linting.composeDetekt)
//    lintChecks(projects.lint)

    ksp(libs.hilt.compiler)

    // Core
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.ui)

    // Features
    implementation(projects.feature.home)
}
