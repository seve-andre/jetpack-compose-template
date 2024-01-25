package com.mitch.appname.util

import androidx.annotation.StringRes
import com.mitch.appname.R

enum class AppTheme(
    @StringRes val translationId: Int
) {
    FollowSystem(R.string.system_default),
    Light(R.string.light_theme),
    Dark(R.string.dark_theme);

    companion object {
        fun default(): AppTheme {
            return FollowSystem
        }
    }
}
