package com.mitch.template.domain.models

enum class TemplateThemePreference {
    FollowSystem,
    Light,
    Dark;

    companion object {
        val Default: TemplateThemePreference = FollowSystem
    }
}
