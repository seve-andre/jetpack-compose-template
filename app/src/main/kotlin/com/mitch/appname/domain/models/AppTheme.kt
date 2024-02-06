package com.mitch.appname.domain.models

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
