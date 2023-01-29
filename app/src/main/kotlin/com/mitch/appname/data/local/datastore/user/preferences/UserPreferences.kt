package com.mitch.appname.data.local.datastore.user.preferences

import com.mitch.appname.util.AppLanguage
import com.mitch.appname.util.AppTheme

data class UserPreferences(
    val language: AppLanguage,
    val theme: AppTheme
)
