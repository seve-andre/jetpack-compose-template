plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.library.compose)
}

android {
    namespace = "com.mitch.template.core.ui"
}

dependencies {
    api(projects.core.domain)
    api(projects.core.designsystem)

    implementation(libs.compose.material3.windowSizeClass)
}
