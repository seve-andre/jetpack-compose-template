package com.mitch.appname.data.local.datastore.user.preferences

import com.mitch.appname.ProtoUserPreferences
import com.mitch.appname.util.AppTheme

/**
 * [UserPreferences] is a local data class used to map/demap to/from [ProtoUserPreferences].
 *
 * @property theme to store user's app theme preference
 */
data class UserPreferences(
    val theme: AppTheme
)
