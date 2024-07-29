plugins {
    alias(libs.plugins.template.jvm.library)
    alias(libs.plugins.template.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
