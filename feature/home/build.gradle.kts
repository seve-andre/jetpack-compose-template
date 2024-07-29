plugins {
    alias(libs.plugins.template.android.feature)
    alias(libs.plugins.template.android.library.compose)
}

android {
    namespace = "com.mitch.template.feature.home"
}

dependencies {
    implementation(projects.core.util)
}
