package com.mitch.appname.util

import java.util.Locale

enum class AppLanguage(private val languageTag: String) {
    ENGLISH("en"),
    ITALIAN("it");

    fun toLocale(): Locale {
        return Locale.forLanguageTag(this.languageTag)
    }
}
