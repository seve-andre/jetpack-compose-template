<p align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="https://tabris.com/wp-content/uploads/2021/06/jetpack-compose-icon_RGB.png" alt="Logo" width="200" height="200">
  </a>
</p>

# Android Jetpack Compose Clean Architecture template

## What does it use?
### Dependencies
- [Kotlin](https://kotlinlang.org/) as main language
- [Jetpack Compose](https://developer.android.com/jetpack/compose) as modern toolkit for native UI
- [Material components for Jetpack Compose](https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#top-level-functions) to build UI faster
- [Room](https://developer.android.com/training/data-storage/room) as local persistent DB
- [Retrofit+OkHttp](https://square.github.io/retrofit/): for api calls
- [Datastore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=CjwKCAjwkYGVBhArEiwA4sZLuMMCRUnWZzzy-AwDePYTUTn3gO6-rrT8jGo7D-H2vztegIJ-zEsb8hoCtI8QAvD_BwE&gclsrc=aw.ds) to cache user info, instead of SharedPreferences since it's ~~deprecated~~
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection
- [Accompanist](https://google.github.io/accompanist/) to use components not included in Compose
- [Detekt](https://detekt.dev/) for static code analysis and code formatting

### Gradle and buildSrc
- Kotlin DSL: instead of Groovy to make a 100% Kotlin-based template
- buildSrc: it includes all `Versions`, `Dependencies`, `Plugins` and `AppConfig` for better gradle dependencies management

## Inspo
- general structure: https://github.com/PizzaMarinara/yaat
- buildSrc: https://github.com/PizzaMarinara/yaat
- [DependencyHandler extensions](https://github.com/nridwan/android_compose_buildsrc/blob/main/src/main/kotlin/Libraries.kt): https://github.com/nridwan/android_compose_buildsrc
