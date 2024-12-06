package com.mitch.template.data.userprefs

import com.mitch.template.data.userprefs.UserPreferencesProtoModel.TemplateThemePreferenceProto
import com.mitch.template.domain.models.TemplateThemePreference

fun TemplateThemePreference.toProtoModel(): TemplateThemePreferenceProto = when (this) {
    TemplateThemePreference.FollowSystem -> TemplateThemePreferenceProto.FOLLOW_SYSTEM
    TemplateThemePreference.Light -> TemplateThemePreferenceProto.LIGHT
    TemplateThemePreference.Dark -> TemplateThemePreferenceProto.DARK
}

fun TemplateThemePreferenceProto.toDomainModel(): TemplateThemePreference = when (this) {
    TemplateThemePreferenceProto.LIGHT -> TemplateThemePreference.Light
    TemplateThemePreferenceProto.DARK -> TemplateThemePreference.Dark
    TemplateThemePreferenceProto.UNRECOGNIZED,
    TemplateThemePreferenceProto.FOLLOW_SYSTEM -> TemplateThemePreference.FollowSystem
}
