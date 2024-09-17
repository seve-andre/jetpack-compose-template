import com.mitch.template.util.implementation
import com.mitch.template.util.library
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("template-android-library"))
                apply(libs.plugin("template-hilt"))
            }

            dependencies {
                implementation(project(":core:ui"))
                implementation(project(":core:designsystem"))

                implementation(libs.library("hilt-navigation-compose"))
                implementation(libs.library("lifecycle-runtimeCompose"))
                implementation(libs.library("lifecycle-viewModel-compose"))
            }
        }
    }
}
