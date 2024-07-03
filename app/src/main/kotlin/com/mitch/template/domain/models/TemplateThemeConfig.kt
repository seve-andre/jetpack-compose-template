package com.mitch.template.domain.models

enum class TemplateThemeConfig {
    FollowSystem,
    Light,
    Dark;

    companion object {
        val Default: TemplateThemeConfig = FollowSystem
    }
}
