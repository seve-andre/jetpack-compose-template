package com.mitch.template.ui.designsystem.components.snackbars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
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
import com.mitch.template.ui.designsystem.TemplateDesignSystem
import com.mitch.template.ui.designsystem.TemplateIcons
import com.mitch.template.ui.designsystem.theme.custom.padding

@Composable
fun TemplateSnackbar(
    colors: TemplateSnackbarColors,
    icon: ImageVector?,
    message: String,
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null,
    dismissAction: @Composable (() -> Unit)? = null,
    actionOnNewLine: Boolean = false,
    shape: Shape = SnackbarDefaults.shape
) {
    Snackbar(
        modifier = modifier,
        action = action,
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

@Preview
@Composable
private fun TemplateSnackbarDefaultPreview() {
    TemplateSnackbar(
        message = "Default",
        action = {
            TextButton(onClick = { }) {
                Text(text = "This is my action")
            }
        },
        colors = TemplateSnackbarDefaults.defaultSnackbarColors(),
        icon = null
    )
}

@Preview
@Composable
private fun TemplateSnackbarDefaultIndefinitePreview() {
    TemplateSnackbar(
        message = "Default",
        action = {
            TextButton(onClick = { }) {
                Text(text = "This is my action")
            }
        },
        colors = TemplateSnackbarDefaults.defaultSnackbarColors(),
        icon = null,
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

@Preview
@Composable
private fun TemplateSnackbarSuccessPreview() {
    TemplateSnackbar(
        message = "Success",
        colors = TemplateSnackbarDefaults.successSnackbarColors(),
        icon = TemplateIcons.Filled.Success
    )
}

@Preview
@Composable
private fun TemplateSnackbarWarningPreview() {
    TemplateSnackbar(
        message = "Warning",
        colors = TemplateSnackbarDefaults.warningSnackbarColors(),
        icon = TemplateIcons.Filled.Warning
    )
}

@Preview
@Composable
private fun TemplateSnackbarErrorPreview() {
    TemplateSnackbar(
        message = "Error",
        colors = TemplateSnackbarDefaults.errorSnackbarColors(),
        icon = TemplateIcons.Filled.Error
    )
}
