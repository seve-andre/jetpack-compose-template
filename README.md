# YAAT - Yet Another Android Template

[![Use this template](https://img.shields.io/badge/from-yaat-brightgreen?logo=dropbox)](https://github.com/PizzaMarinara/yaat/generate) ![License](https://img.shields.io/github/license/PizzaMarinara/yaat) ![Language](https://img.shields.io/github/languages/top/PizzaMarinara/yaat?color=blue&logo=kotlin)

Just wanted to share my own version of an **Android/Kotlin** project template, with a few base classes/interfaces and a bit of a package structure.

## How to use 👣

Just click on [![Use this template](https://img.shields.io/badge/-Use%20this%20template-brightgreen)](https://github.com/PizzaMarinara/yaat/generate) button to create a new repo starting from this template.

Once created don't forget to update the:
- [App ID](buildSrc/src/main/java/Libs.kt)
- AndroidManifest ([here](app/src/main/AndroidManifest.xml))
- Package of the source files

## Features 🎨

- **100% Kotlin-only template**.
- Jetpack Compose + Navigation.
- Package structure already started with a "package by feature" approach.
- 100% Gradle Kotlin DSL setup.
- Dependency versions managed via `buildSrc`.
- Issues Template (bug report + feature request).
- Pull Request Template.

## Gradle Setup 🐘

This template is using [**Gradle Kotlin DSL**](https://docs.gradle.org/current/userguide/kotlin_dsl.html) as well as the [Plugin DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block) to setup the build.

Dependencies are centralized inside the [Libs.kt](buildSrc/src/main/java/Libs.kt) file in the `buildSrc` folder. This provides convenient auto-completion when writing your gradle files.

## Contributing 🤝

Feel free to open a issue or submit a pull request for any bugs/improvements.
