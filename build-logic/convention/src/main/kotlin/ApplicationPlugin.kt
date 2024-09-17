import com.android.build.api.dsl.ApplicationExtension
import com.mitch.template.configureKotlinAndroid
import com.mitch.template.util.Sdk
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("android-application"))
                apply(libs.plugin("kotlin-android"))
                apply(libs.plugin("template-android-lint"))
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Sdk.Latest
            }
        }
    }
}
