package com.mitch.appname.util

// change also gradle android config and locales config
sealed class AppLanguage(val languageTag: String) {
    object English : AppLanguage("en")
    object Italian : AppLanguage("it")
}
