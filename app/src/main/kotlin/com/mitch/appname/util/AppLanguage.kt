package com.mitch.appname.util

sealed class AppLanguage(val languageTag: String) {
    object English : AppLanguage("en")
}
