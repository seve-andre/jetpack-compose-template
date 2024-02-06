package com.mitch.appname.ui.screens.home.components

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.mitch.appname.R
import com.mitch.appname.domain.models.AppLanguage
import com.mitch.appname.ui.designsystem.AppIcons
import com.mitch.appname.ui.designsystem.theme.custom.padding
import okhttp3.internal.toImmutableList

@Composable
fun LanguagePickerDialog(
    selectedLanguage: AppLanguage,
    onDismiss: () -> Unit,
    onConfirm: (AppLanguage) -> Unit
) {
    var tempLanguage by remember { mutableStateOf(selectedLanguage) }

    val items = listOf(
        LanguagePickerItem.English,
        LanguagePickerItem.Italian
    ).toImmutableList()

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = AppIcons.Outlined.Translate,
                contentDescription = null
            )
        },
        title = {
            Text(text = stringResource(id = R.string.change_language))
        },
        text = {
            Column(modifier = Modifier.selectableGroup()) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .selectable(
                                selected = (item.language == tempLanguage),
                                onClick = { tempLanguage = item.language },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = padding.medium),
                        horizontalArrangement = Arrangement.spacedBy(padding.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (item.language == tempLanguage),
                            onClick = null
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(padding.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = item.flagId),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .testTag(item.flagId.toString())
                            )
                            Text(
                                text = item.language.locale.displayLanguage,
                                style = MaterialTheme.typography.bodyLarge
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

sealed class LanguagePickerItem(
    val language: AppLanguage,
    @DrawableRes val flagId: Int
) {
    data object English : LanguagePickerItem(
        language = AppLanguage.English,
        flagId = R.drawable.english_flag
    )

    data object Italian : LanguagePickerItem(
        language = AppLanguage.Italian,
        flagId = R.drawable.italian_flag
    )
}
