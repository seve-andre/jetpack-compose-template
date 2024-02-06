package com.mitch.appname.domain.models

import java.util.Locale

enum class AppLanguage(val locale: Locale) {
    English(locale = Locale.ENGLISH),
    Italian(locale = Locale.ITALIAN);

    companion object {
        fun fromLocale(locale: Locale): AppLanguage {
            return entries.find { it.locale == locale } ?: default()
        }

        fun default(): AppLanguage {
            return English
        }
    }
}
