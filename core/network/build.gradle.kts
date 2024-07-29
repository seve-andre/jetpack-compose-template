plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.mitch.template.core.network"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(projects.core.domain)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    implementation(libs.kotlinx.serialization.json)
}
