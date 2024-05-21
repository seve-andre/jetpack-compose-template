package com.mitch.template.ui.navigation

import kotlinx.serialization.Serializable

object TemplateDestinations {

    sealed interface Screen {
        @Serializable
        data object Home : Screen
    }

    sealed interface Graph
}
