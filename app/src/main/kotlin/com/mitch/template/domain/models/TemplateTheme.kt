package com.mitch.template.domain.models

enum class TemplateTheme {
    FollowSystem,
    Light,
    Dark;

    companion object {
        fun default(): TemplateTheme {
            return FollowSystem
        }
    }
}
