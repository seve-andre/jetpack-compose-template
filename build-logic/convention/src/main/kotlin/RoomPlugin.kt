import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import com.mitch.template.util.implementation
import com.mitch.template.util.ksp
import com.mitch.template.util.library
import com.mitch.template.util.libs
import com.mitch.template.util.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugin("room"))
                apply(libs.plugin("ksp"))
            }

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
            }

            extensions.configure<RoomExtension> {
                // The schemas directory contains a schema file for each version of the Room database.
                // This is required to enable Room auto migrations.
                // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration.
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                implementation(libs.library("room-runtime"))
                implementation(libs.library("room-ktx"))
                ksp(libs.library("room-compiler"))
            }
        }
    }
}
