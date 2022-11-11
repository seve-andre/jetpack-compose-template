object Dependencies {
    private const val gradlePluginVersion = "7.3.1"
    const val androidGradlePlugin = "com.android.tools.build:gradle:$gradlePluginVersion"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.1.5"

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"

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
        const val systemUiController =
            "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"

        const val placeholder =
            "com.google.accompanist:accompanist-placeholder:${Versions.accompanist}"
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
        const val proto = "androidx.datastore:datastore:1.0.0"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }

    object AndroidX {
        object Lifecycle {
            private const val version = "2.5.1"

            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
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
