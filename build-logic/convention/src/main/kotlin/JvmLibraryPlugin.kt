import com.mitch.template.configureKotlinJvm
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("kotlin-jvm"))
                apply(libs.plugin("template-android-lint"))
            }
            configureKotlinJvm()
        }
    }
}
