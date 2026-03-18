import com.android.build.api.variant.BuildConfigField
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.StringReader
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.detekt)
    alias(libs.plugins.junit5)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.square.wire)
}

val packageName = "com.mitch.template"

enum class TemplateBuildType(val applicationIdSuffix: String? = null) {
    Debug(applicationIdSuffix = ".debug"),
    Staging(applicationIdSuffix = ".staging"),
    Release;

    val typeName = this.name.replaceFirstChar { it.lowercase() }
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

android {
    namespace = packageName

    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = packageName
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        listOf(
            TemplateBuildType.Staging,
            TemplateBuildType.Release
        ).forEach { type ->
            providers.fileContents(
                isolated.rootProject.projectDirectory.dir("config/signing")
                    .file("signing.${type.typeName}.properties")
            ).asText.map { text ->
                val properties = Properties().apply {
                    load(StringReader(text))
                }

                register(type.typeName) {
                    keyAlias = properties["keyAlias"] as String
                    keyPassword = properties["keyPassword"] as String
                    storeFile = file(properties["storeFile"] as String)
                    storePassword = properties["storePassword"] as String
                }
            }
        }
    }
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = TemplateBuildType.Debug.applicationIdSuffix
        }
        register(TemplateBuildType.Staging.typeName) {
            initWith(named(TemplateBuildType.Release.typeName).get())
            isDebuggable = true
            applicationIdSuffix = TemplateBuildType.Staging.applicationIdSuffix
            signingConfig = try {
                signingConfigs.named(TemplateBuildType.Staging.typeName).get()
            } catch (_: Exception) {
                null
            }
        }
        release {
            applicationIdSuffix = TemplateBuildType.Release.applicationIdSuffix
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = try {
                signingConfigs.named(TemplateBuildType.Release.typeName).get()
            } catch (_: Exception) {
                null
            }
        }
    }
    flavorDimensions += TemplateFlavorDimension.values().map { it.dimensionName }
    productFlavors {
        TemplateFlavor.values().forEach { flavor ->
            register(flavor.flavorName) {
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

private val javaVarRegexp = Regex(pattern = "[^a-zA-Z_$0-9]")
androidComponents {
    onVariants { variant ->
        val secretsProps = providers.fileContents(
            isolated.rootProject.projectDirectory.dir("config/secrets")
                .file("secrets.${variant.buildType}.properties")
        ).asBytes.map { bytes ->
            Properties().apply {
                load(bytes.inputStream())
            }
        }.orElse(Properties())

        secretsProps.get()
            .keys
            .map { it as String }
            .filter { it.isNotEmpty() }
            .forEach { key ->
                val rawValue = secretsProps.get().getProperty(key)
                val value = rawValue.removeSurrounding("\"")
                val sanitizedKey = key.replace(javaVarRegexp, "")

                val (type, buildValue) = when {
                    value.equals("true", ignoreCase = true) -> "boolean" to true
                    value.equals("false", ignoreCase = true) -> "boolean" to false
                    value.endsWith("L") && value.dropLast(1).toLongOrNull() != null ->
                        "long" to value.dropLast(1).toLong()

                    value.toIntOrNull() != null -> "int" to value.toInt()
                    value.toFloatOrNull() != null -> "float" to value.toFloat()
                    else -> "String" to value.addQuotesIfNeeded()
                }

                variant.buildConfigFields?.put(
                    sanitizedKey,
                    BuildConfigField(type = type, value = buildValue, comment = null)
                )
                variant.manifestPlaceholders.put(sanitizedKey, value)
            }
    }
}

private fun String.addQuotesIfNeeded() = when {
    isEmpty() -> this
    length >= 2 && startsWith('"') && endsWith('"') -> this
    else -> "\"$this\""
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
    reportsDestination =
        layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFiles.addAll(
        isolated.rootProject.projectDirectory.file("compose_compiler_config.conf")
    )
}

detekt {
    config.setFrom("${isolated.rootProject}/config/detekt/detekt.yml")
    baseline = file("${isolated.rootProject}/config/detekt/baseline.xml")
    autoCorrect = true
    buildUponDefaultConfig = true
    parallel = true
}

tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        markdown.required.set(true) // simple Markdown format
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("resources/")
    exclude("build/")
    jvmTarget = JvmTarget.JVM_17.target
}
tasks.withType<dev.detekt.gradle.DetektCreateBaselineTask>().configureEach {
    jvmTarget = JvmTarget.JVM_17.target
}

wire {
    kotlin {
        android = true
    }
}

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.collectionsImmutable)
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
    implementation(libs.lifecycle.viewModel.nav3)
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
    implementation(libs.nav3.runtime)
    implementation(libs.nav3.ui)

    // Database
    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    // Datastore (previously SharedPreferences)
    implementation(libs.datastore.core)

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
    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.junit5.api)
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
