package com.mitch.template.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface TemplateNavKey : NavKey {

    @Serializable
    data object Home : TemplateNavKey
}
