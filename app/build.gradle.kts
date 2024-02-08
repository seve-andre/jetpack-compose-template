import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.junit5)
}

val packageName = "com.mitch.appname"

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
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    sourceSets {
        debug {
            kotlin.srcDir("build/generated/ksp/debug/kotlin")
        }
        release {
            kotlin.srcDir("build/generated/ksp/release/kotlin")
        }
    }
}

ksp {
    arg("compose-destinations.codeGenPackageName", "$packageName.navigation")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/config/detekt/detekt.yml")

    // REMOVE once edited RepositoryModule and AppDatabase
    ignoreFailures = true

    autoCorrect = true
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

// workaround for ksp "error.NonExistentClass" https://github.com/google/dagger/issues/4158#issuecomment-1825440083
androidComponents {
    onVariants(selector().all()) { variant ->
        afterEvaluate {
            val capName = variant.name.capitalized()
            tasks.getByName<KotlinCompile>("ksp${capName}Kotlin") {
                setSource(tasks.getByName("generate${capName}Proto").outputs)
            }
        }
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        md.required.set(true) // simple Markdown format
    }
}

tasks.getByPath("preBuild").dependsOn("detekt")

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.immutableCollections)
    implementation(libs.kotlinx.datetime)
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
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    // Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    // Room
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    // Datastore (previously SharedPreferences)
    implementation(libs.datastore.proto)
    implementation(libs.protobuf.kotlin.lite)

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
    detektPlugins(libs.detekt)
    lintChecks(libs.linting.composeLints)
}
