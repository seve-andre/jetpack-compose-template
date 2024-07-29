plugins {
    alias(libs.plugins.template.android.library)
    alias(libs.plugins.template.hilt)
}

android {
    namespace = "com.mitch.template.core.data"
}

dependencies {
    api(projects.core.datastore)
    api(projects.core.network)
    api(projects.core.util)

    implementation(libs.appcompat)
}
