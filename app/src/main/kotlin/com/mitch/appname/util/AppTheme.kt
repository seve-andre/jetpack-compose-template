package com.mitch.appname.util

sealed class AppTheme {
    object FollowSystem : AppTheme()
    object Light : AppTheme()
    object Dark : AppTheme()
}
