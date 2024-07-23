import com.android.build.api.dsl.ApplicationExtension
import com.mitch.template.configureKotlinAndroid
import com.mitch.template.util.Sdk
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("template.android.lint")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Sdk.Latest
            }
        }
    }

}
