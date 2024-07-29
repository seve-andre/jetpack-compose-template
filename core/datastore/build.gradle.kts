plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.mitch.template.core.datastore"
}

dependencies {
    api(projects.core.domain)
    implementation(projects.core.util)

    api(libs.datastore.core)
    implementation(libs.kotlinx.serialization.protobuf)
}
