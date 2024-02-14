package com.mitch.appname.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val theme: AppTheme = AppTheme.FollowSystem
)
