import com.mitch.template.util.Libs
import com.mitch.template.util.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("template.android.library")
                apply("template.hilt")
            }

            dependencies {
                /*implementation(project(":core:ui"))
                implementation(project(":core:designsystem"))*/

                implementation(Libs.findLibrary("hilt-navigation-compose").get())
                implementation(Libs.findLibrary("lifecycle-runtimeCompose").get())
                implementation(Libs.findLibrary("lifecycle-viewModel-compose").get())
            }
        }
    }
}
