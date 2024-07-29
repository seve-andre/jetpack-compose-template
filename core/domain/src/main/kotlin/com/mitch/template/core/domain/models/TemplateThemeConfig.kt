package com.mitch.template.core.domain.models

enum class TemplateThemeConfig {
    FollowSystem,
    Light,
    Dark;

    companion object {
        val Default: TemplateThemeConfig = FollowSystem
    }
}
