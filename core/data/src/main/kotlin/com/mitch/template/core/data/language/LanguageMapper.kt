package com.mitch.template.core.data.language

import com.mitch.template.core.domain.models.TemplateLanguageConfig
import java.util.Locale

fun Locale.toDomainLanguage(): TemplateLanguageConfig {
    // removes country code and variants if present
    val localeLanguageOnly = Locale.forLanguageTag(this.language)

    return TemplateLanguageConfig.fromLocale(localeLanguageOnly)
}
