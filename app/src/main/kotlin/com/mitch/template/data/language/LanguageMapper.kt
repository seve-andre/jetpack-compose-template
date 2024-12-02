package com.mitch.template.data.language

import com.mitch.template.domain.models.TemplateLanguagePreference
import java.util.Locale

fun Locale.toDomainModel(): TemplateLanguagePreference {
    // removes country code and variants if present
    val localeLanguageOnly = Locale.forLanguageTag(this.language)

    return TemplateLanguagePreference.fromLocale(localeLanguageOnly)
}
