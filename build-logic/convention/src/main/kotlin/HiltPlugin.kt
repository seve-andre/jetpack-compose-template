
import com.android.build.gradle.api.AndroidBasePlugin
import com.mitch.template.util.Libs
import com.mitch.template.util.implementation
import com.mitch.template.util.ksp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")
            dependencies {
                ksp(Libs.findLibrary("hilt-compiler").get())
                implementation(Libs.findLibrary("hilt-core").get())
            }

            /** Add support for Android modules, based on [AndroidBasePlugin] */
            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply("dagger.hilt.android.plugin")
                dependencies {
                    implementation(Libs.findLibrary("hilt-android").get())
                }
            }
        }
    }
}
