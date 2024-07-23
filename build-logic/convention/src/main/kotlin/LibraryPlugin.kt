import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.mitch.template.configureFlavors
import com.mitch.template.configureKotlinAndroid
import com.mitch.template.util.Libs
import com.mitch.template.util.Sdk
import com.mitch.template.util.androidTestImplementation
import com.mitch.template.util.implementation
import com.mitch.template.util.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("template.android.lint")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Sdk.Latest
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                configureFlavors(this)
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix = path
                    .split("""\W""".toRegex())
                    .drop(1)
                    .distinct()
                    .joinToString(separator = "_")
                    .lowercase() + "_"
            }
            dependencies {
                androidTestImplementation(kotlin("test"))
                testImplementation(kotlin("test"))
            }
        }
    }
}
