import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.kotlin() {
    implementation(Dependencies.Kotlin.stdlib)
    implementation(Dependencies.Kotlin.Coroutines.android)
}

fun DependencyHandler.ui() {
    implementation(Dependencies.Compose.activity)
    implementation(Dependencies.Compose.runtime)
    implementation(Dependencies.Compose.material2)
    implementation(Dependencies.Compose.foundation)
    implementation(Dependencies.Compose.layout)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.animation)
    implementation(Dependencies.Compose.iconsExtended)

    // Uncomment to use Material 3 over 2
    // implementation(Dependencies.Compose.material3)
    // implementation(Dependencies.Compose.material3WindowSize)

    implementation(Dependencies.Accompanist.systemUiController)
    implementation(Dependencies.Accompanist.placeholder)

    implementation(Dependencies.AndroidX.Lifecycle.viewModelCompose)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.Navigation.compose)
    ksp(Dependencies.Navigation.ksp)
}

fun DependencyHandler.network() {
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.okhttp)
    implementation(Dependencies.Network.okhttpLogging)
    implementation(Dependencies.Network.moshiConverter)
}

fun DependencyHandler.dependencyInjection() {
    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)
}

private fun DependencyHandler.localDB() {
    implementation(Dependencies.Room.runtime)
    kapt(Dependencies.Room.compiler)
    implementation(Dependencies.Room.ktx)
}

private fun DependencyHandler.datastore() {
    implementation(Dependencies.Datastore.proto)
}

fun DependencyHandler.localCaching() {
    localDB()
    datastore()
}

fun DependencyHandler.logging() {
    implementation(Dependencies.timber)
}

fun DependencyHandler.tests() {
    testImplementation(Dependencies.Tests.junit)
    androidTestImplementation(Dependencies.Tests.androidJUnit)
    androidTestImplementation(Dependencies.Tests.espressoCore)
    androidTestImplementation(Dependencies.Tests.composeJUnit)
}

// extensions to add dependencies
fun DependencyHandler.implementation(depName: String) =
    add("implementation", depName)

fun DependencyHandler.kapt(depName: String) = add("kapt", depName)
fun DependencyHandler.ksp(depName: String) = add("ksp", depName)
fun DependencyHandler.compileOnly(depName: String) = add("compileOnly", depName)
fun DependencyHandler.api(depName: String) = add("api", depName)

fun DependencyHandler.testImplementation(depName: String) =
    add("testImplementation", depName)

fun DependencyHandler.androidTestImplementation(depName: String) =
    add("androidTestImplementation", depName)

fun DependencyHandler.debugImplementation(depName: String) =
    add("debugImplementation", depName)
