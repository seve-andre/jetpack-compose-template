package com.mitch.template.domain.models

import java.util.Locale

enum class TemplateLanguagePreference(val locale: Locale?) {
    FollowSystem(locale = null),
    English(locale = Locale.ENGLISH),
    Italian(locale = Locale.ITALIAN);

    companion object {
        val Default: TemplateLanguagePreference = FollowSystem

        fun fromLocale(locale: Locale): TemplateLanguagePreference {
            return entries.find { it.locale == locale } ?: Default
        }
    }
}
