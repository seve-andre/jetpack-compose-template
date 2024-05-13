package com.mitch.template.data.language

import com.mitch.template.domain.models.TemplateLanguage
import java.util.Locale

fun Locale.toDomainLanguage(): TemplateLanguage {
    // removes country code and variants if present
    val localeLanguageOnly = Locale.forLanguageTag(this.language)

    return TemplateLanguage.fromLocale(localeLanguageOnly)
}
