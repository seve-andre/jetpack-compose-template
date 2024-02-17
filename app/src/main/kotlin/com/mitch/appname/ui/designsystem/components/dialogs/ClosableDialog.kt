package com.mitch.appname.ui.designsystem.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.AlertDialogDefaults.textContentColor
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.mitch.appname.ui.designsystem.AppIcons
import com.mitch.appname.ui.designsystem.theme.custom.padding
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Close

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClosableDialog(
    onDismiss: () -> Unit,
    title: @Composable () -> Unit,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: (@Composable () -> Unit)? = null,
    confirmButton: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    properties: DialogProperties = DialogProperties()
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        properties = properties
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(padding.medium)
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = padding.small)
                        .align(Alignment.End)
                ) {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        tooltip = {
                            PlainTooltip {
                                Text("Close dialog")
                            }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = EvaIcons.Outline.Close,
                                contentDescription = "Close dialog"
                            )
                        }
                    }
                }

                icon?.let {
                    Box(
                        modifier = Modifier
                            .padding(bottom = padding.medium)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        icon()
                    }
                }

                CompositionLocalProvider(LocalContentColor provides titleContentColor) {
                    val textStyle = MaterialTheme.typography.headlineSmall
                    ProvideTextStyle(textStyle) {
                        Box(
                            modifier = Modifier
                                .padding(bottom = padding.medium)
                                .align(
                                    if (icon == null) {
                                        Alignment.Start
                                    } else {
                                        Alignment.CenterHorizontally
                                    }
                                )
                        ) {
                            title()
                        }
                    }
                }

                CompositionLocalProvider(LocalContentColor provides textContentColor) {
                    val textStyle = MaterialTheme.typography.bodyMedium
                    ProvideTextStyle(textStyle) {
                        Row(
                            modifier = Modifier
                                .weight(weight = 1f, fill = false)
                                .padding(bottom = 24.dp)
                                .align(Alignment.Start)
                        ) {
                            body()
                        }
                    }
                }

                if (dismissButton != null && confirmButton != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        dismissButton()
                        Spacer(Modifier.width(10.dp))
                        confirmButton()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BasicClosableDialogPreview() {
    ClosableDialog(
        onDismiss = { },
        title = {
            Text(text = "Basic dialog title")
        },
        body = {
            Text(text = "A dialog is a type of modal window that appears")
        },
        confirmButton = {
            TextButton(onClick = { }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { }) {
                Text(text = "Dismiss")
            }
        }
    )
}

@Preview
@Composable
private fun ClosableDialogWithHeroIconPreview() {
    ClosableDialog(
        onDismiss = { },
        icon = {
            Icon(
                imageVector = AppIcons.Outlined.Translate,
                contentDescription = null
            )
        },
        title = {
            Text(text = "Basic dialog title")
        },
        body = {
            Text(text = "A dialog is a type of modal window that appears")
        },
        confirmButton = {
            TextButton(onClick = { }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { }) {
                Text(text = "Dismiss")
            }
        }
    )
}
