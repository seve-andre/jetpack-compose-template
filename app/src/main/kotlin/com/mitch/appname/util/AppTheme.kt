package com.mitch.appname.util

enum class AppTheme {
    FollowSystem,
    Light,
    Dark;

    companion object {
        fun default(): AppTheme {
            return FollowSystem
        }
    }
}
