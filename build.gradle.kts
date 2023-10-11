plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.junit5) apply false
    alias(libs.plugins.android.test) apply false

    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

apply("${project.rootDir}/buildscripts/toml-updater-config.gradle")
