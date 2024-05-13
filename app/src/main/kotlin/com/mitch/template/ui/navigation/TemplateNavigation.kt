package com.mitch.template.ui.navigation

import kotlinx.serialization.Serializable

object TemplateNavigation {

    sealed interface Screen {
        @Serializable
        data object Home : Screen

        @Serializable
        data class Profile(val id: Int) : Screen
    }

    sealed interface Graph
}
