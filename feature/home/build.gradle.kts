plugins {
    alias(libs.plugins.template.android.feature)
    alias(libs.plugins.template.android.compose)
}

android {
    namespace = "com.mitch.template.feature.home"
}

dependencies {
    implementation(projects.core.util)
    androidTestImplementation(libs.bundles.compose.ui.test)
}
