package com.mitch.appname.data.mapper

import com.mitch.appname.ProtoUserPreferences.ProtoAppTheme
import com.mitch.appname.util.AppTheme

fun AppTheme.toProto(): ProtoAppTheme = when (this) {
    AppTheme.FollowSystem -> ProtoAppTheme.FOLLOW_SYSTEM
    AppTheme.Light -> ProtoAppTheme.LIGHT
    AppTheme.Dark -> ProtoAppTheme.DARK
}

fun ProtoAppTheme.toLocal(): AppTheme = when (this) {
    ProtoAppTheme.LIGHT -> AppTheme.Light
    ProtoAppTheme.DARK -> AppTheme.Dark
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> AppTheme.FollowSystem
}
