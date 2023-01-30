object Dependencies {
    private const val gradlePluginVersion = "7.3.1"
    const val androidGradlePlugin = "com.android.tools.build:gradle:$gradlePluginVersion"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"
    const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val evaIcons = "br.com.devsrsouza.compose.icons.android:eva-icons:1.0.0"
    const val splashscreen = "androidx.core:core-splashscreen:1.0.0"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

        object Coroutines {
            private const val version = "1.6.0"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Compose {
        const val activity = "androidx.activity:activity-compose:1.6.1"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val material2 = "androidx.compose.material:material:${Versions.compose}"
        const val material3 = "androidx.compose.material3:material3:1.0.1"
        const val material3WindowSize = "androidx.compose.material3:material3-window-size-class:1.0.1"
        const val coil = "io.coil-kt:coil-compose:2.2.2"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val animation = "androidx.compose.animation:animation:${Versions.compose}"
        const val iconsExtended =
            "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    }

    object Accompanist {
        // no swipeRefresh; now Modifier.pullRefresh() can be used;
        // see https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier#(androidx.compose.ui.Modifier).pullRefresh(androidx.compose.material.pullrefresh.PullRefreshState,kotlin.Boolean)

        const val systemUiController =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"

        const val placeholder =
            "com.google.accompanist:accompanist-placeholder-material:${Versions.accompanist}"
    }

    object Navigation {
        const val compose = "io.github.raamcosta.compose-destinations:core:1.7.25-beta"
        const val ksp = "io.github.raamcosta.compose-destinations:ksp:1.7.25-beta"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
    }

    object Datastore {
        // Datastore + proto
        const val proto = "androidx.datastore:datastore:${Versions.protoDatastore}"
        const val protoCompiler = "com.google.protobuf:protoc:${Versions.protobuf}"
        const val kotlinLite = "com.google.protobuf:protobuf-kotlin-lite:${Versions.protobuf}"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

        // hiltViewModel()
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object AndroidX {
        object Lifecycle {
            private const val version = "2.5.1"

            // SavedStateHandle
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"

            // collectAsStateWithLifecycle()
            const val runtimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:2.6.0-alpha03"
        }
    }

    object Tests {
        const val junit = "junit:junit:4.13.2"
        const val androidJUnit = "androidx.test.ext:junit-ktx:1.1.4"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        const val composeJUnit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    }
}
