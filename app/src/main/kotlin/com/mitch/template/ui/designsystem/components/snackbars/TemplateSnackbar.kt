package com.mitch.template.ui.designsystem.components.snackbars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.mitch.template.ui.designsystem.TemplateDesignSystem
import com.mitch.template.ui.designsystem.TemplateIcons
import com.mitch.template.ui.designsystem.TemplateTheme
import com.mitch.template.ui.designsystem.theme.custom.padding

@Composable
fun TemplateSnackbar(
    colors: TemplateSnackbarColors,
    message: String,
    icon: ImageVector?,
    action: SnackbarAction?,
    modifier: Modifier = Modifier,
    dismissAction: @Composable (() -> Unit)? = null,
    actionOnNewLine: Boolean = false,
    shape: Shape = SnackbarDefaults.shape
) {
    Snackbar(
        modifier = modifier,
        action = action?.let {
            {
                TextButton(
                    onClick = action.onPerformAction,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = colors.actionColor
                    )
                ) {
                    Text(text = action.label)
                }
            }
        },
        dismissAction = dismissAction,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = colors.containerColor,
        contentColor = colors.messageColor,
        actionContentColor = colors.actionColor
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(padding.small))
            }
            Text(text = message)
        }
    }
}

@PreviewLightDark
@Composable
private fun TemplateSnackbarDefaultPreview() {
    TemplateTheme {
        TemplateSnackbar(
            message = "Default",
            icon = null,
            action = SnackbarAction(label = "This is my action", onPerformAction = { }),
            colors = TemplateSnackbarDefaults.defaultSnackbarColors()
        )
    }
}

@Preview
@Composable
private fun TemplateSnackbarDefaultIndefinitePreview() {
    TemplateTheme {
        TemplateSnackbar(
            message = "Default",
            icon = null,
            action = SnackbarAction(label = "This is my action", onPerformAction = { }),
            colors = TemplateSnackbarDefaults.defaultSnackbarColors(),
            dismissAction = {
                IconButton(
                    onClick = { },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = TemplateDesignSystem.colorScheme.inverseOnSurface
                    )
                ) {
                    Icon(
                        imageVector = TemplateIcons.Outlined.Close,
                        contentDescription = "Dismiss snackbar"
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun TemplateSnackbarSuccessPreview() {
    TemplateTheme {
        TemplateSnackbar(
            message = "Success",
            icon = TemplateIcons.Filled.Success,
            action = null,
            colors = TemplateSnackbarDefaults.successSnackbarColors()
        )
    }
}

@Preview
@Composable
private fun TemplateSnackbarWarningPreview() {
    TemplateTheme {
        TemplateSnackbar(
            message = "Warning",
            icon = TemplateIcons.Filled.Warning,
            action = null,
            colors = TemplateSnackbarDefaults.warningSnackbarColors()
        )
    }
}

@Preview
@Composable
private fun TemplateSnackbarErrorPreview() {
    TemplateTheme {
        TemplateSnackbar(
            message = "Error",
            icon = TemplateIcons.Filled.Error,
            action = null,
            colors = TemplateSnackbarDefaults.errorSnackbarColors()
        )
    }
}
