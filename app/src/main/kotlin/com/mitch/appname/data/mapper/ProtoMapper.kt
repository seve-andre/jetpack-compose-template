package com.mitch.appname.data.mapper

import com.mitch.appname.ProtoUserPreferences
import com.mitch.appname.ProtoUserPreferences.ProtoAppTheme
import com.mitch.appname.data.local.datastore.user.preferences.UserPreferences
import com.mitch.appname.util.AppTheme

fun ProtoUserPreferences.toLocalUserPreferences(): UserPreferences {
    return UserPreferences(
        this.theme.toLocal()
    )
}

fun AppTheme.toProto(): ProtoAppTheme = when (this) {
    AppTheme.Light -> ProtoAppTheme.LIGHT
    AppTheme.Dark -> ProtoAppTheme.DARK
    AppTheme.FollowSystem -> ProtoAppTheme.FOLLOW_SYSTEM
}

fun ProtoAppTheme.toLocal(): AppTheme = when (this) {
    ProtoAppTheme.LIGHT -> AppTheme.Light
    ProtoAppTheme.DARK -> AppTheme.Dark
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> AppTheme.FollowSystem
}
