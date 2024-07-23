import com.android.build.api.dsl.ApplicationExtension
import com.mitch.template.configureFlavors
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationFlavorsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<ApplicationExtension> {
                configureFlavors(this)
            }
        }
    }
}
