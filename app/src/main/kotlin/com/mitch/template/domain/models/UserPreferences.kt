package com.mitch.template.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val theme: TemplateTheme = TemplateTheme.FollowSystem
)
