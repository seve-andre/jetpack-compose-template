import com.android.build.gradle.TestExtension
import com.mitch.template.configureKotlinAndroid
import com.mitch.template.util.Sdk
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("android-test"))
                apply(libs.plugin("kotlin-android"))
            }

            extensions.configure<TestExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Sdk.Latest
            }
        }
    }
}
