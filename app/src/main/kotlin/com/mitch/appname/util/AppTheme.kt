package com.mitch.appname.util

import androidx.annotation.StringRes
import com.mitch.appname.R

enum class AppTheme(
    @StringRes val translationId: Int
) {
    FOLLOW_SYSTEM(R.string.system_default),
    LIGHT(R.string.light_theme),
    DARK(R.string.dark_theme);

    companion object {
        fun default(): AppTheme {
            return FOLLOW_SYSTEM
        }
    }
}
