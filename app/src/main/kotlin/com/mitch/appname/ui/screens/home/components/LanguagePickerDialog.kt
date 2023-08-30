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
import com.mitch.appname.R
import com.mitch.appname.util.AppLanguage
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Globe
import java.util.Locale

@Composable
fun LanguagePickerDialog(
    selectedLocale: Locale,
    onDismiss: () -> Unit,
    onConfirm: (Locale) -> Unit
) {
    var tempLocale by remember { mutableStateOf(selectedLocale) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(EvaIcons.Outline.Globe, contentDescription = null) },
        title = {
            Text(text = stringResource(R.string.change_language))
        },
        text = {
            Column(Modifier.selectableGroup()) {
                AppLanguage.values().forEach { language ->
                    val languageLocale = language.toLocale()
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (languageLocale == tempLocale),
                                onClick = { tempLocale = languageLocale },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (languageLocale == tempLocale),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = languageLocale.displayLanguage,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(tempLocale)
                    onDismiss()
                },
                enabled = tempLocale != selectedLocale
            ) {
                Text(stringResource(R.string.save))
            }
        }
    )
}
