package com.mitch.appname.util

/*
 * Modify also:
 * - app build.gradle.kts android defaultConfig resourceConfigurations
 * - res/xml/locales_config.xml
 */
sealed class AppLanguage(val languageTag: String) {
    object English : AppLanguage("en")
    object Italian : AppLanguage("it")
}
