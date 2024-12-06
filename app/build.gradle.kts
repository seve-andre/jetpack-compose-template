import com.android.build.api.dsl.ApkSigningConfig
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.detekt)
    alias(libs.plugins.junit5)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.secrets)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.protobuf)
}

val packageName = "com.mitch.template"

enum class TemplateBuildType(val applicationIdSuffix: String? = null) {
    Debug(".debug"),
    Staging(".staging"),
    Release
}

enum class TemplateFlavorDimension {
    Version;

    val dimensionName = this.name.replaceFirstChar { it.lowercase() }
}

enum class TemplateFlavor(
    val dimension: TemplateFlavorDimension,
    val applicationIdSuffix: String? = null
) {
    Demo(dimension = TemplateFlavorDimension.Version),
    Prod(dimension = TemplateFlavorDimension.Version);

    val flavorName = this.name.replaceFirstChar { it.lowercase() }
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = packageName

    compileSdk = 35
    defaultConfig {
        applicationId = packageName
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        androidResources {
            generateLocaleConfig = true
        }
    }
    signingConfigs {
        if (!keystoreProperties.isEmpty) {
            createSigningConfig("staging", keystoreProperties)
            createSigningConfig("release", keystoreProperties)
        }
    }
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = TemplateBuildType.Debug.applicationIdSuffix
        }
        create("staging") {
            initWith(getByName("release"))
            isDebuggable = true
            applicationIdSuffix = TemplateBuildType.Staging.applicationIdSuffix
            secrets.propertiesFileName = "secrets.staging.properties"
            signingConfig = try {
                signingConfigs.named("staging").get()
            } catch (e: Exception) {
                null
            }
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            secrets.propertiesFileName = "secrets.release.properties"
            signingConfig = try {
                signingConfigs.named("release").get()
            } catch (e: Exception) {
                null
            }
        }
    }
    flavorDimensions += TemplateFlavorDimension.values().map { it.dimensionName }
    productFlavors {
        TemplateFlavor.values().forEach { flavor ->
            create(flavor.flavorName) {
                dimension = flavor.dimension.dimensionName
                if (flavor.applicationIdSuffix != null) {
                    applicationIdSuffix = flavor.applicationIdSuffix
                }
            }
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    bundle {
        language {
            enableSplit = false
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

fun NamedDomainObjectContainer<out ApkSigningConfig>.createSigningConfig(
    name: String,
    properties: Properties
) {
    create(name) {
        keyAlias = properties["${name}KeyAlias"] as String
        keyPassword = properties["${name}KeyPassword"] as String
        storeFile = file(properties["storeFile"] as String)
        storePassword = properties["storePassword"] as String
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
        freeCompilerArgs.add(
            // Enable experimental coroutines APIs, including Flow
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        )
        freeCompilerArgs.add(
            /**
             * Remove this args after Phase 3.
             * https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-consistent-copy-visibility/#deprecation-timeline
             *
             * Deprecation timeline
             * Phase 3. (Supposedly Kotlin 2.2 or Kotlin 2.3).
             * The default changes.
             * Unless ExposedCopyVisibility is used, the generated 'copy' method has the same visibility as the primary constructor.
             * The binary signature changes. The error on the declaration is no longer reported.
             * '-Xconsistent-data-class-copy-visibility' compiler flag and ConsistentCopyVisibility annotation are now unnecessary.
             */
            "-Xconsistent-data-class-copy-visibility"
        )
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile =
        rootProject.layout.projectDirectory.file("compose_compiler_config.conf")
}

detekt {
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    baseline = file("$rootDir/config/detekt/baseline.xml")
    autoCorrect = true
    buildUponDefaultConfig = true
    parallel = true
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        md.required.set(true) // simple Markdown format
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("resources/")
    exclude("build/")
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
    propertiesFileName = "secrets.properties"
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // UI (Compose + Accompanist + Icons + ...)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.ui.graphics)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.animation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowSizeClass)
    implementation(libs.lifecycle.viewModel.compose)
    implementation(libs.lifecycle.viewModel.savedstate)
    implementation(libs.lifecycle.runtimeCompose)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.icons.material.core)
    implementation(libs.icons.material.extended)
    implementation(libs.icons.eva)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)

    // Navigation
    implementation(libs.compose.navigation)

    // Database
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    // Datastore (previously SharedPreferences)
    implementation(libs.datastore.core)
    implementation(libs.protobuf.kotlin.lite)

    // Logging
    implementation(libs.timber)

    // API calls
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.resources)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.serialization.kotlinxJson)
    implementation(libs.ktor.client.auth)

    // Testing
    // Unit
    testImplementation(libs.junit4)
    testImplementation(libs.junit5)
    testRuntimeOnly(libs.junit5.engine)
    testImplementation(libs.junit5.params)
    testImplementation(libs.assertk)
    testImplementation(libs.turbine)
    // Instrumented
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.composeTest.junit4)
    debugImplementation(libs.composeTest.manifest)
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.uiAutomator)

    // Desugaring - https://developer.android.com/studio/write/java8-support-table
    coreLibraryDesugaring(libs.android.desugarJdkLibs)

    // Formatting + Linting
    detektPlugins(libs.detekt.formatting)
    detektPlugins(libs.linting.composeDetekt)
    lintChecks(projects.lint)
}
