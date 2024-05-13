package com.mitch.template.domain.models

import java.util.Locale

enum class TemplateLanguage(val locale: Locale) {
    English(locale = Locale.ENGLISH),
    Italian(locale = Locale.ITALIAN);

    companion object {
        fun fromLocale(locale: Locale): TemplateLanguage {
            return entries.find { it.locale == locale } ?: default()
        }

        fun default(): TemplateLanguage {
            return English
        }
    }
}
