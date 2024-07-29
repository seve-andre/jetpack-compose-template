import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
    alias(libs.plugins.detekt)
    alias(libs.plugins.junit5)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.secrets)
    alias(libs.plugins.compose.compiler)
}

val packageName = "com.mitch.template"

enum class TemplateFlavorDimension {
    Version;

    val dimensionName = this.name.replaceFirstChar { it.lowercase() }
}

enum class TemplateFlavor(
    val dimension: TemplateFlavorDimension,
    val applicationIdSuffix: String? = null
) {
    Demo(dimension = TemplateFlavorDimension.Version),
    Prod(dimension = TemplateFlavorDimension.Version);

    val flavorName = this.name.replaceFirstChar { it.lowercase() }
}

android {
    namespace = packageName

    compileSdk = 34
    defaultConfig {
        applicationId = packageName
        minSdk = 21
        targetSdk = 34
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
    flavorDimensions += TemplateFlavorDimension.values().map { it.dimensionName }
    productFlavors {
        TemplateFlavor.values().forEach { flavor ->
            create(flavor.flavorName) {
                dimension = flavor.dimension.dimensionName
                if (flavor.applicationIdSuffix != null) {
                    applicationIdSuffix = flavor.applicationIdSuffix
                }
            }
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    bundle {
        language {
            enableSplit = false
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

composeCompiler {
    enableStrongSkippingMode = true
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile =
        rootProject.layout.projectDirectory.file("compose_compiler_config.conf")
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
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // UI (Compose + Accompanist + Icons + ...)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.ui.graphics)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.animation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowSizeClass)
    implementation(libs.lifecycle.viewModel.compose)
    implementation(libs.lifecycle.viewModel.savedstate)
    implementation(libs.lifecycle.runtimeCompose)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.icons.material.core)
    implementation(libs.icons.material.extended)
    implementation(libs.icons.eva)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)

    // Navigation
    implementation(libs.compose.navigation)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Database
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    // Datastore (previously SharedPreferences)
    implementation(libs.datastore.core)

    // Logging
    implementation(libs.timber)

    // API calls
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)

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

    // Desugaring - https://developer.android.com/studio/write/java8-support-table
    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    // Formatting + Linting
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.linting.composeDetekt)
    lintChecks(projects.lint)
}
