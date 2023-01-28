package com.mitch.appname.util

sealed class AppTheme {
    object Light : AppTheme()
    object Dark : AppTheme()
}
