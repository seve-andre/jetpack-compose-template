package com.mitch.template.data.userprefs

import com.mitch.template.data.userprefs.ProtoUserPreferences.ProtoAppTheme
import com.mitch.template.domain.models.TemplateThemePreference

fun TemplateThemePreference.toProtoModel(): ProtoAppTheme = when (this) {
    TemplateThemePreference.FollowSystem -> ProtoAppTheme.FOLLOW_SYSTEM
    TemplateThemePreference.Light -> ProtoAppTheme.LIGHT
    TemplateThemePreference.Dark -> ProtoAppTheme.DARK
}

fun ProtoAppTheme.toDomainModel(): TemplateThemePreference = when (this) {
    ProtoAppTheme.LIGHT -> TemplateThemePreference.Light
    ProtoAppTheme.DARK -> TemplateThemePreference.Dark
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> TemplateThemePreference.FollowSystem
}
