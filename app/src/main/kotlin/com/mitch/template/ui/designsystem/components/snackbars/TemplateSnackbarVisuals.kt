package com.mitch.template.ui.designsystem.components.snackbars

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mitch.template.ui.designsystem.TemplateIcons
import com.mitch.template.ui.designsystem.theme.custom.extendedColorScheme

data class TemplateSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = duration == SnackbarDuration.Indefinite,
    val type: TemplateSnackbarType = TemplateSnackbarType.Default,
    val imageVector: ImageVector? = when (type) {
        TemplateSnackbarType.Default -> null
        TemplateSnackbarType.Success -> TemplateIcons.Filled.Success
        TemplateSnackbarType.Warning -> TemplateIcons.Filled.Warning
        TemplateSnackbarType.Error -> TemplateIcons.Filled.Error
    }
) : SnackbarVisuals

enum class TemplateSnackbarType {
    Default,
    Success,
    Warning,
    Error
}

data class TemplateSnackbarColors(
    val containerColor: Color,
    val iconColor: Color,
    val messageColor: Color,
    val actionColor: Color
)

object TemplateSnackbarDefaults {

    @Composable
    fun defaultSnackbarColors(
        containerColor: Color = SnackbarDefaults.color,
        messageColor: Color = SnackbarDefaults.contentColor,
        actionColor: Color = SnackbarDefaults.actionContentColor,
        iconColor: Color = SnackbarDefaults.contentColor
    ): TemplateSnackbarColors {
        return TemplateSnackbarColors(
            containerColor = containerColor,
            messageColor = messageColor,
            actionColor = actionColor,
            iconColor = iconColor
        )
    }

    @Composable
    fun successSnackbarColors(
        containerColor: Color = MaterialTheme.extendedColorScheme.success,
        messageColor: Color = MaterialTheme.extendedColorScheme.onSuccess,
        actionColor: Color = MaterialTheme.extendedColorScheme.onSuccess,
        iconColor: Color = actionColor
    ): TemplateSnackbarColors {
        return TemplateSnackbarColors(
            containerColor = containerColor,
            messageColor = messageColor,
            actionColor = actionColor,
            iconColor = iconColor
        )
    }

    @Composable
    fun warningSnackbarColors(
        containerColor: Color = MaterialTheme.extendedColorScheme.warning,
        messageColor: Color = MaterialTheme.extendedColorScheme.onWarning,
        actionColor: Color = MaterialTheme.extendedColorScheme.onWarning,
        iconColor: Color = actionColor
    ): TemplateSnackbarColors {
        return TemplateSnackbarColors(
            containerColor = containerColor,
            messageColor = messageColor,
            actionColor = actionColor,
            iconColor = iconColor
        )
    }

    @Composable
    fun errorSnackbarColors(
        containerColor: Color = MaterialTheme.colorScheme.errorContainer,
        messageColor: Color = MaterialTheme.colorScheme.onErrorContainer,
        actionColor: Color = MaterialTheme.colorScheme.onErrorContainer,
        iconColor: Color = actionColor
    ): TemplateSnackbarColors {
        return TemplateSnackbarColors(
            containerColor = containerColor,
            messageColor = messageColor,
            actionColor = actionColor,
            iconColor = iconColor
        )
    }
}
