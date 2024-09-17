import com.android.build.gradle.api.AndroidBasePlugin
import com.mitch.template.util.implementation
import com.mitch.template.util.ksp
import com.mitch.template.util.library
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugin("ksp"))
            dependencies {
                ksp(libs.library("hilt-compiler"))
            }

            // Add support for Jvm Module, base on org.jetbrains.kotlin.jvm
            pluginManager.withPlugin(libs.plugin("kotlin-jvm")) {
                dependencies {
                    implementation(libs.library("hilt-core"))
                }
            }

            /** Add support for Android modules, based on [AndroidBasePlugin] */
            pluginManager.withPlugin("com.android.base") {
                pluginManager.apply(libs.plugin("hilt"))
                dependencies {
                    implementation(libs.library("hilt-android"))
                }
            }
        }
    }
}
