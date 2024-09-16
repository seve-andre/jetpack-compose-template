plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.android.compose)
}

android {
    namespace = "com.mitch.template.core.designsystem"
}

dependencies {
    lintPublish(projects.lint)

    api(libs.compose.foundation)
    api(libs.compose.foundation.layout)
    api(libs.compose.material3)
    api(libs.compose.runtime)
    api(libs.compose.ui.util)
    implementation(libs.icons.eva)
    implementation(libs.icons.material.extended)
}
