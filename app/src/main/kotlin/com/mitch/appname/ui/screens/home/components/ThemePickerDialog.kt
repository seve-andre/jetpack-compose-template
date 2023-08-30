package com.mitch.appname.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.mitch.appname.util.AppTheme
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Flag

@Composable
fun ThemePickerDialog(
    selectedTheme: AppTheme,
    onDismiss: () -> Unit,
    onConfirm: (AppTheme) -> Unit
) {
    var tempTheme by remember { mutableStateOf(selectedTheme) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(EvaIcons.Outline.Flag, contentDescription = null) },
        title = {
            Text(text = "Change theme")
        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Column(Modifier.selectableGroup()) {
                    AppTheme.values().forEach { theme ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (theme == tempTheme),
                                    onClick = { tempTheme = theme },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (theme == tempTheme),
                                onClick = null // null recommended for accessibility with screenreaders
                            )
                            Text(
                                text = stringResource(id = theme.translationId),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(tempTheme)
                    onDismiss()
                },
                enabled = tempTheme != selectedTheme
            ) {
                Text("Confirm")
            }
        }
    )
}
