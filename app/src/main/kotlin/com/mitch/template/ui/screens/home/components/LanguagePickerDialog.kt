package com.mitch.template.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mitch.template.R
import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.ui.designsystem.TemplateDesignSystem
import com.mitch.template.ui.designsystem.TemplateIcons
import com.mitch.template.ui.designsystem.TemplateTheme
import com.mitch.template.ui.designsystem.theme.custom.padding

@Composable
fun LanguagePickerDialog(
    selectedLanguage: TemplateLanguagePreference,
    onDismiss: () -> Unit,
    onConfirm: (TemplateLanguagePreference) -> Unit
) {
    var tempLanguage by remember { mutableStateOf(selectedLanguage) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = TemplateIcons.Outlined.Translate,
                contentDescription = null
            )
        },
        title = {
            Text(text = stringResource(id = R.string.change_language))
        },
        text = {
            Column(modifier = Modifier.selectableGroup()) {
                for (languagePreference in TemplateLanguagePreference.entries) {
                    val isSelected = languagePreference == tempLanguage
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .selectable(
                                selected = isSelected,
                                onClick = { tempLanguage = languagePreference },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = padding.medium),
                        horizontalArrangement = Arrangement.spacedBy(padding.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = null
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(padding.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = languagePreference.flag(),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = if (languagePreference.locale == null) {
                                    stringResource(id = R.string.system_default)
                                } else {
                                    languagePreference.locale.displayLanguage
                                },
                                style = TemplateDesignSystem.typography.bodyLarge
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
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(tempLanguage)
                    onDismiss()
                },
                enabled = tempLanguage != selectedLanguage
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    )
}

@Composable
private fun TemplateLanguagePreference.flag(): Painter {
    val flagId = when (this) {
        TemplateLanguagePreference.FollowSystem -> R.drawable.earth_flag
        TemplateLanguagePreference.English -> R.drawable.english_flag
        TemplateLanguagePreference.Italian -> R.drawable.italian_flag
    }
    return painterResource(id = flagId)
}

@PreviewLightDark
@Composable
private fun LanguagePickerDialogPreview() {
    TemplateTheme {
        LanguagePickerDialog(
            selectedLanguage = TemplateLanguagePreference.English,
            onDismiss = { },
            onConfirm = { }
        )
    }
}
