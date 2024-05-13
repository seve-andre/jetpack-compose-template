package com.mitch.appname.ui.navigation

import kotlinx.serialization.Serializable

object AppNavigation {

    sealed interface Screen {
        @Serializable
        data object Home : Screen

        @Serializable
        data class Profile(val id: Int) : Screen
    }

    sealed interface Graph
}
