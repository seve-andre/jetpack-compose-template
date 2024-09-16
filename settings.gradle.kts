pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "template"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":lint")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:domain")
include(":core:designsystem")
include(":core:network")
include(":core:ui")
include(":core:util")
include(":feature:home")
