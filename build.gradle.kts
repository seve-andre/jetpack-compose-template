plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.junit5) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.lint) apply false
}
