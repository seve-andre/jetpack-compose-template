package com.mitch.template.ui.navigation

import kotlinx.serialization.Serializable

sealed interface TemplateDestination {

    sealed interface Screen : TemplateDestination {
        @Serializable
        data object Home : Screen
    }

    sealed interface Graph : TemplateDestination
}
