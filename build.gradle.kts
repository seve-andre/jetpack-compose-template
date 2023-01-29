buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.androidGradlePlugin)
        classpath(Dependencies.Kotlin.gradlePlugin)
        classpath(Dependencies.Hilt.gradlePlugin)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
