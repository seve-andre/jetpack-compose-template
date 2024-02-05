package com.mitch.appname.ui.designsystem.components.snackbars

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.mitch.appname.ui.designsystem.theme.custom.padding

@Composable
fun AppSnackbar(
    colors: AppSnackbarColors,
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
private fun AppSnackbarDefaultPreview() {
    AppSnackbar(
        message = "Default",
        action = {
            TextButton(onClick = { }) {
                Text(text = "This is my action")
            }
        },
        colors = AppSnackbarDefaults.defaultSnackbarColors(),
        icon = null
    )
}

@Preview
@Composable
private fun AppSnackbarDefaultIndefinitePreview() {
    AppSnackbar(
        message = "Default",
        action = {
            TextButton(onClick = { }) {
                Text(text = "This is my action")
            }
        },
        colors = AppSnackbarDefaults.defaultSnackbarColors(),
        icon = null,
        dismissAction = {
            IconButton(
                onClick = { },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss snackbar"
                )
            }
        }
    )
}

@Preview
@Composable
private fun AppSnackbarSuccessPreview() {
    AppSnackbar(
        message = "Success",
        action = null,
        colors = AppSnackbarDefaults.successSnackbarColors(),
        icon = Icons.Rounded.CheckCircle
    )
}

@Preview
@Composable
private fun AppSnackbarWarningPreview() {
    AppSnackbar(
        message = "Warning",
        action = null,
        colors = AppSnackbarDefaults.warningSnackbarColors(),
        icon = Icons.Rounded.Warning
    )
}

@Preview
@Composable
private fun AppSnackbarErrorPreview() {
    AppSnackbar(
        message = "Error",
        action = null,
        colors = AppSnackbarDefaults.errorSnackbarColors(),
        icon = Icons.Rounded.Close
    )
}
