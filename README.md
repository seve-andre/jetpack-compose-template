<p align="center">
  <a href="https://developer.android.com/jetpack/compose">
    <img src="https://tabris.com/wp-content/uploads/2021/06/jetpack-compose-icon_RGB.png" alt="Logo" width="200" height="200">
  </a>
</p>

# Android Jetpack Compose template
Click on [![Use this template](https://img.shields.io/badge/-Use%20this%20template-%23347d39)](https://github.com/seve-andre/compose-template/generate) to create your own repo
## :warning: Warning
The template consists of 2 branches:
- [main](https://github.com/seve-andre/compose-template/tree/main): uses Material3 since it's the latest design system
- [material2](https://github.com/seve-andre/compose-template/tree/material2): uses Material2, older but still strong

## What to update
- root project name in `setting.gradle.kts`
- package name (now *com.mitch.appname*)
- `applicationId` in `buildSrc/AppConfig.kt`
- app name in `res/values/strings.xml`
- `AndroidManifest.xml` (in app/src/main)
- `AppName.kt` to your app full name (should match android.name in AndroidManifest.xml)
- `AppTheme` to your app name followed by "Theme" in `ui/theme/Theme.kt`
- `detekt.yml` rules (in app/config/detekt)
- dependencies, their versions, plugins, android configuration (appId, minSdk, targetSdk, compileSdk) in `buildSrc`
- `java_package` option in `user_preferences.proto`
- languages supported by the app in:
  - `res/xml/locales_config.xml`
  - in app `build.gradle.kts` (android &#8594; defaultConfig &#8594; resourceConfigurations) and in `util/AppLanguage.kt`; they should all match
  - **NOTE**: you should also create the `res/values-*language tag*` folder contaning `strings.xml`
    > [here](https://android.googlesource.com/platform/frameworks/base/+/refs/tags/android-13.0.0_r41/core/res/res/values/locale_config.xml) is a list of all the supported language tags as of Android 13
- `AppDatabase.kt` in `data/local/db` to your app name followed by "Database"
- `appname.db` and `providesAppDatabase()` in `di/DatabaseModule.kt`
- remove `ignoreFailures = true` inside the detekt block in the app `build.gradle.kts` file and fix the errors
- `Make Project` to generate all the files needed to run the app
- run the app and you should see the splashscreen followed by the blank home screen

## What does it use?
### Dependencies
- [Kotlin](https://kotlinlang.org/) as main language
- [Jetpack Compose](https://developer.android.com/jetpack/compose) as modern toolkit for native UI
- [Material components for Jetpack Compose](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#top-level-functions) to build UI faster
- [Room](https://developer.android.com/training/data-storage/room) as local persistent DB
- [Retrofit+OkHttp](https://square.github.io/retrofit/): for api calls
- [Datastore](https://developer.android.com/topic/libraries/architecture/datastore?gclid=CjwKCAjwkYGVBhArEiwA4sZLuMMCRUnWZzzy-AwDePYTUTn3gO6-rrT8jGo7D-H2vztegIJ-zEsb8hoCtI8QAvD_BwE&gclsrc=aw.ds) to cache user info, instead of SharedPreferences since it's ~~deprecated~~
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injection
- [Accompanist](https://google.github.io/accompanist/) to use components not included in Compose
  - [System UI Controller](https://google.github.io/accompanist/systemuicontroller/) to change status and navigation bar colors
  - [Placeholder](https://google.github.io/accompanist/placeholder/) to create a skeleton loader effect when loading a screen
- [Detekt](https://detekt.dev/) for static code analysis and formatting
- [Timber](https://github.com/JakeWharton/timber) for logging
- [Eva icons](https://github.com/DevSrSouza/compose-icons/blob/master/eva-icons/DOCUMENTATION.md) for the icons to use throughout the whole app
- [Coil](https://coil-kt.github.io/coil/compose/) for image loading backed by Kotlin coroutines
- [Splashscreen API](https://developer.android.com/develop/ui/views/launch/splash-screen) to display a splashscreen at app startup
- [Per-app language preferences](https://developer.android.com/guide/topics/resources/app-languages) to change the app language inside the app or in the system settings
- [Compose Destinations](https://composedestinations.rafaelcosta.xyz/) for easier app navigation

### Gradle and buildSrc
- Kotlin DSL: instead of Groovy to make a 100% Kotlin-based template
- buildSrc: it includes all `Versions`, `Dependencies`, `Plugins` and `AppConfig` for better gradle dependencies management

## Inspo
- general structure: [see inspo](https://github.com/PizzaMarinara/yaat)
- buildSrc: [see inspo](https://github.com/PizzaMarinara/yaat)
- [SnackbarController](https://github.com/seve-andre/compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/util/SnackbarController.kt): [see inspo](https://github.com/mitchtabian/MVVMRecipeApp/blob/managing-snackbar-with-scaffold/app/src/main/java/com/codingwithmitch/mvvmrecipeapp/presentation/components/util/SnackbarController.kt)
- [Result](https://github.com/seve-andre/compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/ui/util/Result.kt): [see inspo](https://github.com/android/nowinandroid/blob/607c24e7f7399942e278af663ea4ad350e5bbc3a/core/common/src/main/java/com/google/samples/apps/nowinandroid/core/result/Result.kt)
- [AppState](https://github.com/seve-andre/compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/ui/util/AppState.kt): [see inspo](https://github.com/android/nowinandroid/blob/607c24e7f7399942e278af663ea4ad350e5bbc3a/app/src/main/java/com/google/samples/apps/nowinandroid/ui/NiaAppState.kt)
- [NetworkMonitor](https://github.com/seve-andre/compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/util/network/NetworkMonitor.kt): [see inspo](https://github.com/android/nowinandroid/blob/9371d0d4b80ffea0105a2376d057243eb68af0fa/core/data/src/main/java/com/google/samples/apps/nowinandroid/core/data/util/NetworkMonitor.kt)
- [ConnectivityManagerNetworkMonitor](https://github.com/seve-andre/compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/util/network/ConnectivityManagerNetworkMonitor.kt): [see inspo](https://github.com/android/nowinandroid/blob/11fbf53f12898b6ee7c55dda69716fa3600e7317/core/data/src/main/java/com/google/samples/apps/nowinandroid/core/data/util/ConnectivityManagerNetworkMonitor.kt)
