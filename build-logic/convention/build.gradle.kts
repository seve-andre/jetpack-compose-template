import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.mitch.template.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidCompose") {
            id = "template.android.compose"
            implementationClass = "ComposePlugin"
        }
        register("androidApplication") {
            id = "template.android.application"
            implementationClass = "ApplicationPlugin"
        }
        register("androidFeature") {
            id = "template.android.feature"
            implementationClass = "FeaturePlugin"
        }
        register("androidFlavors") {
            id = "template.android.application.flavors"
            implementationClass = "ApplicationFlavorsPlugin"
        }
        register("androidLibrary") {
            id = "template.android.library"
            implementationClass = "LibraryPlugin"
        }
        register("androidLint") {
            id = "template.android.lint"
            implementationClass = "LintPlugin"
        }
        register("androidRoom") {
            id = "template.android.room"
            implementationClass = "RoomPlugin"
        }
        register("androidTest") {
            id = "template.android.test"
            implementationClass = "TestPlugin"
        }
        register("hilt") {
            id = "template.hilt"
            implementationClass = "HiltPlugin"
        }
        register("jvmLibrary") {
            id = "template.jvm.library"
            implementationClass = "JvmLibraryPlugin"
        }
    }
}
