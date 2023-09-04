package com.mitch.appname.data.mapper

import com.mitch.appname.ProtoUserPreferences.ProtoAppTheme
import com.mitch.appname.util.AppTheme

fun AppTheme.toProto(): ProtoAppTheme = when (this) {
    AppTheme.FOLLOW_SYSTEM -> ProtoAppTheme.FOLLOW_SYSTEM
    AppTheme.LIGHT -> ProtoAppTheme.LIGHT
    AppTheme.DARK -> ProtoAppTheme.DARK
}

fun ProtoAppTheme.toLocal(): AppTheme = when (this) {
    ProtoAppTheme.LIGHT -> AppTheme.LIGHT
    ProtoAppTheme.DARK -> AppTheme.DARK
    ProtoAppTheme.UNRECOGNIZED, ProtoAppTheme.FOLLOW_SYSTEM -> AppTheme.FOLLOW_SYSTEM
}
