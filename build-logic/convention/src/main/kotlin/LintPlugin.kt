import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LintPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            when {
                pluginManager.hasPlugin(libs.plugin("android-application")) ->
                    configure<ApplicationExtension> { lint(Lint::configure) }

                pluginManager.hasPlugin(libs.plugin("android-library")) ->
                    configure<LibraryExtension> { lint(Lint::configure) }

                else -> {
                    pluginManager.apply(libs.plugin("android-lint"))
                    configure<Lint>(Lint::configure)
                }
            }
        }
    }
}

private fun Lint.configure() {
    checkDependencies = true
}
