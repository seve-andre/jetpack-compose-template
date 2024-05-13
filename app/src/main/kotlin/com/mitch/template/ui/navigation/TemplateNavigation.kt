package com.mitch.template.ui.navigation

import kotlinx.serialization.Serializable

object TemplateNavigation {

    sealed interface Screen {
        @Serializable
        data object Home : Screen
    }

    sealed interface Graph
}
