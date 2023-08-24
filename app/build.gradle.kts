plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.kapt)
}

android {
    val packageName = "com.mitch.appname"
    namespace = packageName

    compileSdk = 34
    defaultConfig {
        applicationId = packageName
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resourceConfigurations.addAll(listOf("en", "it"))
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
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

kapt {
    correctErrorTypes = true
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config = files("$projectDir/config/detekt/detekt.yml")

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
    implementation(libs.kotlinx.coroutines.android)

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
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.lifecycle.viewModelCompose)
    implementation(libs.lifecycle.runtimeCompose)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.icons.eva)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)

    // Navigation
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

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
    testImplementation(libs.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.compose.ui.test.manifest)
    debugImplementation(libs.compose.ui.test.manifest)

    // Desugaring - https://developer.android.com/studio/write/java8-support-table
    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    // Formatting
    detektPlugins(libs.formatting.detekt)

    // Linting
    lintChecks(libs.linting.composeLints)
}
