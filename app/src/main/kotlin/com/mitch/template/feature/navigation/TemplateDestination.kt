package com.mitch.template.feature.navigation

import kotlinx.serialization.Serializable

sealed interface TemplateDestination {

    sealed interface Screen : TemplateDestination {
        @Serializable
        data object Home : Screen
    }

    sealed interface Graph : TemplateDestination
}
