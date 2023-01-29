package com.mitch.appname.data.mapper

import com.mitch.appname.ProtoUserPreferences
import com.mitch.appname.ProtoUserPreferences.ProtoAppLanguage
import com.mitch.appname.ProtoUserPreferences.ProtoAppTheme
import com.mitch.appname.data.local.datastore.user.preferences.UserPreferences
import com.mitch.appname.util.AppLanguage
import com.mitch.appname.util.AppTheme

fun ProtoUserPreferences.toLocalUserPreferences(): UserPreferences {
    return UserPreferences(
        this.language.toLocal(),
        this.theme.toLocal()
    )
}

fun AppLanguage.toProto(): ProtoAppLanguage = when (this) {
    AppLanguage.English -> ProtoAppLanguage.ENGLISH
}

fun AppTheme.toProto(): ProtoAppTheme = when (this) {
    AppTheme.Light -> ProtoAppTheme.LIGHT
    AppTheme.Dark -> ProtoAppTheme.DARK
}

fun ProtoAppLanguage.toLocal(): AppLanguage = when (this) {
    ProtoAppLanguage.ENGLISH -> AppLanguage.English
    ProtoAppLanguage.UNRECOGNIZED -> AppLanguage.English
}

fun ProtoAppTheme.toLocal(): AppTheme = when (this) {
    ProtoAppTheme.LIGHT -> AppTheme.Light
    ProtoAppTheme.DARK -> AppTheme.Dark
    ProtoAppTheme.UNRECOGNIZED -> AppTheme.Light
}
