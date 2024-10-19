package com.mitch.template.data.userprefs

import com.mitch.template.data.userprefs.ProtoUserPreferences.ProtoAppTheme
import com.mitch.template.domain.models.TemplateThemeConfig

fun TemplateThemeConfig.toProtoModel(): ProtoAppTheme = when (this) {
    TemplateThemeConfig.FollowSystem -> ProtoAppTheme.FOLLOW_SYSTEM
    TemplateThemeConfig.Light -> ProtoAppTheme.LIGHT
    TemplateThemeConfig.Dark -> ProtoAppTheme.DARK
}

fun ProtoAppTheme.toDomainModel(): TemplateThemeConfig = when (this) {
    ProtoAppTheme.LIGHT -> TemplateThemeConfig.Light
    ProtoAppTheme.DARK -> TemplateThemeConfig.Dark
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> TemplateThemeConfig.FollowSystem
}
