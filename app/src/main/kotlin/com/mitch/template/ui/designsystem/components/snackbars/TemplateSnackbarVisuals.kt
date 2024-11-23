package com.mitch.template.ui.designsystem.components.snackbars

import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mitch.template.ui.designsystem.TemplateDesignSystem
import com.mitch.template.ui.designsystem.TemplateIcons
import com.mitch.template.ui.designsystem.theme.custom.extendedColorScheme

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction? = null,
    val onDismiss: (() -> Unit)? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val type: TemplateSnackbarType = TemplateSnackbarType.Default,
    val imageVector: ImageVector? = when (type) {
        TemplateSnackbarType.Default -> null
        TemplateSnackbarType.Success -> TemplateIcons.Filled.Success
        TemplateSnackbarType.Warning -> TemplateIcons.Filled.Warning
        TemplateSnackbarType.Error -> TemplateIcons.Filled.Error
    }
)

data class SnackbarAction(val label: String, val onPerformAction: () -> Unit)

data class TemplateSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    val onPerformAction: (() -> Unit)? = null,
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

fun SnackbarEvent.toVisuals(): TemplateSnackbarVisuals {
    return TemplateSnackbarVisuals(
        message = this.message,
        actionLabel = this.action?.label,
        duration = this.duration,
        type = this.type,
        imageVector = this.imageVector
    )
}

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
        containerColor: Color = TemplateDesignSystem.extendedColorScheme.success,
        messageColor: Color = TemplateDesignSystem.extendedColorScheme.onSuccess,
        actionColor: Color = TemplateDesignSystem.extendedColorScheme.onSuccess,
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
        containerColor: Color = TemplateDesignSystem.extendedColorScheme.warning,
        messageColor: Color = TemplateDesignSystem.extendedColorScheme.onWarning,
        actionColor: Color = TemplateDesignSystem.extendedColorScheme.onWarning,
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
        containerColor: Color = TemplateDesignSystem.colorScheme.errorContainer,
        messageColor: Color = TemplateDesignSystem.colorScheme.onErrorContainer,
        actionColor: Color = TemplateDesignSystem.colorScheme.onErrorContainer,
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
