package com.mitch.template.domain.models

import java.util.Locale

enum class TemplateLanguageConfig(val locale: Locale) {
    English(locale = Locale.ENGLISH),
    Italian(locale = Locale.ITALIAN);

    companion object {
        val Default: TemplateLanguageConfig = English

        fun fromLocale(locale: Locale): TemplateLanguageConfig {
            return entries.find { it.locale == locale } ?: Default
        }
    }
}
