package com.mitch.appname.ui.designsystem.components.snackbars

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mitch.appname.ui.designsystem.AppIcons
import com.mitch.appname.ui.designsystem.theme.custom.extendedColorScheme

data class AppSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = duration == SnackbarDuration.Indefinite,
    val type: AppSnackbarType = AppSnackbarType.Default,
    val imageVector: ImageVector? = when (type) {
        AppSnackbarType.Default -> null
        AppSnackbarType.Success -> AppIcons.Filled.Success
        AppSnackbarType.Warning -> AppIcons.Filled.Warning
        AppSnackbarType.Error -> AppIcons.Filled.Error
    }
) : SnackbarVisuals

enum class AppSnackbarType {
    Default,
    Success,
    Warning,
    Error
}

data class AppSnackbarColors(
    val containerColor: Color,
    val iconColor: Color,
    val messageColor: Color,
    val actionColor: Color
)

object AppSnackbarDefaults {

    @Composable
    fun defaultSnackbarColors(
        containerColor: Color = SnackbarDefaults.color,
        messageColor: Color = SnackbarDefaults.contentColor,
        actionColor: Color = SnackbarDefaults.actionContentColor,
        iconColor: Color = SnackbarDefaults.contentColor
    ): AppSnackbarColors {
        return AppSnackbarColors(
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
    ): AppSnackbarColors {
        return AppSnackbarColors(
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
    ): AppSnackbarColors {
        return AppSnackbarColors(
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
    ): AppSnackbarColors {
        return AppSnackbarColors(
            containerColor = containerColor,
            messageColor = messageColor,
            actionColor = actionColor,
            iconColor = iconColor
        )
    }
}
