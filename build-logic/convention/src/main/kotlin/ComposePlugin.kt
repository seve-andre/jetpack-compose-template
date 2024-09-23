import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.mitch.template.configureAndroidCompose
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugin("compose-compiler"))

            val extension: CommonExtension<*, *, *, *, *, *> = when {
                pluginManager.hasPlugin(libs.plugin("android-application")) -> the<ApplicationExtension>()
                pluginManager.hasPlugin(libs.plugin("android-library")) -> the<LibraryExtension>()
                else -> TODO("This plugin is dependent on either template.android.application or template.android.library. Apply one of those plugins first.")
            }
            configureAndroidCompose(extension)
        }
    }
}
