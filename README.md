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

## Instructions
Assuming:
1. domain name = `com.mitch`
2. app name = `Todo`

### What to update
- in [settings.gradle.kts](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/settings.gradle.kts):
  ```gradle.kts
    // BEFORE
    rootProject.name = "appname"
    
    // AFTER
    rootProject.name = "todo"
  ```

- package name from `com.mitch.appname` to `com.mitch.todo` [see how](https://stackoverflow.com/a/29092698/15696479)

- in [AppConfig.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/buildSrc/src/main/kotlin/AppConfig.kt):
  ```kotlin
    // BEFORE
    const val applicationId = "com.mitch.appname"
    
    // AFTER
    const val applicationId = "com.mitch.todo"
  ```

- in [user_preferences.proto](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/proto/user_preferences.proto):
  ```proto
    // BEFORE
    option java_package = "com.mitch.appname";
    
    // AFTER
    option java_package = "com.mitch.todo";
  ```

- rename [AppName.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/AppName.kt) to `TodoApplication.kt`

- in [AndroidManifest.xml](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/AndroidManifest.xml):
  ```xml
    <!-- BEFORE -->
    android:name=".AppName"
    
    <!-- AFTER -->
    android:name=".TodoApplication"
  ```

- in [Theme.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/ui/theme/Theme.kt):
  ```kotlin
    // BEFORE
    fun AppTheme()
    
    // AFTER
    fun TodoTheme()
  ```
- rename [AppLanguage.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/util/AppLanguage.kt) to `TodoLanguage.kt` and its sealed class:
  ```kotlin
    // BEFORE
    sealed class AppLanguage()
    
    // AFTER
    sealed class TodoLanguage()
  ```

- rename [AppTheme.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/util/AppTheme.kt) to `TodoTheme.kt` and its sealed class:
  ```kotlin
    // BEFORE
    sealed class AppTheme()
    
    // AFTER
    sealed class TodoTheme()
  ```

- in [strings.xml](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/res/values/strings.xml):
  ```xml
    <!-- BEFORE -->
    <string name="app_name">template</string>
    
    <!-- AFTER -->
    <string name="app_name">Todo</string>
  ```
  > :warning: do this for every strings.xml file (i.e. for every app supported language)

- app supported languages in:
  - [locales_config.xml](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/res/xml/locales_config.xml):

  - [build.gradle.kts](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/build.gradle.kts)
    - change the language tags in `resourceConfigurations.addAll(listOf("en", "it"))`
  
  - [AppLanguage.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/util/AppLanguage.kt)
  
  > :warning: all the language tags should match in the 3 points above
  
  > :warning: you must also create the `res/values-*language tag*` folder containing the `strings.xml` file for each language
  
  > :100: [here](https://android.googlesource.com/platform/frameworks/base/+/refs/tags/android-13.0.0_r41/core/res/res/values/locale_config.xml) is a list of all the supported language tags as of Android 13

- rename [AppDatabase.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/data/local/db/AppDatabase.kt) to `TodoDatabase.kt` and its abstract class:
  ```kotlin
    // BEFORE
    abstract class AppDatabase : RoomDatabase() {
    }
    
    // AFTER
    abstract class TodoDatabase : RoomDatabase() {
    }
  ```

- `appname.db` and `providesAppDatabase()` in `di/DatabaseModule.kt`

- in [DatabaseModule.kt](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/di/DatabaseModule.kt):
  ```kotlin
    // BEFORE
    fun providesAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "appname.db"
        ).build()
    }
    
    // AFTER
    fun providesTodoDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(
            appContext,
            TodoDatabase::class.java,
            "todo.db"
        ).build()
    }
  
  ```

- to change formatting rules, edit [detekt.yml](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/config/detekt/detekt.yml)

- to change dependencies, their versions, plugins and android configuration, edit the files in [this folder](https://github.com/seve-andre/android-jetpack-compose-template/tree/main/buildSrc/src/main/kotlin)

### Next steps
- `Make Project` to generate all the files needed to run the app
- in [build.gradle.kts](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/build.gradle.kts) remove `ignoreFailures = true` and fix the errors
- run the app and you should see the Splashscreen followed by the blank [HomeScreen](https://github.com/seve-andre/android-jetpack-compose-template/blob/main/app/src/main/kotlin/com/mitch/appname/ui/HomeScreen.kt)

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
