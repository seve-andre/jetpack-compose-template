package com.mitch.template.ui.screens.home.components

import androidx.annotation.StringRes
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.mitch.template.R
import com.mitch.template.domain.models.TemplateThemePreference
import com.mitch.template.ui.designsystem.TemplateDesignSystem
import com.mitch.template.ui.designsystem.TemplateIcons
import com.mitch.template.ui.designsystem.TemplateTheme
import com.mitch.template.ui.designsystem.theme.custom.padding

@Composable
fun ThemePickerDialog(
    selectedTheme: TemplateThemePreference,
    onDismiss: () -> Unit,
    onConfirm: (TemplateThemePreference) -> Unit
) {
    var tempTheme by remember { mutableStateOf(selectedTheme) }

    val items = listOf(
        ThemePickerItem.FollowSystem,
        ThemePickerItem.Light,
        ThemePickerItem.Dark
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = TemplateIcons.Outlined.Palette,
                contentDescription = null
            )
        },
        title = {
            Text(text = stringResource(id = R.string.change_theme))
        },
        text = {
            Column(modifier = Modifier.selectableGroup()) {
                for (item in items) {
                    val isSelected = item.theme == tempTheme
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .selectable(
                                selected = isSelected,
                                onClick = { tempTheme = item.theme },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = padding.medium),
                        horizontalArrangement = Arrangement.spacedBy(padding.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(padding.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .testTag(item.icon.toString())
                            )
                            Text(
                                text = stringResource(id = item.titleId),
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
                Text(stringResource(R.string.cancel))
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
                Text(stringResource(R.string.save))
            }
        }
    )
}

sealed class ThemePickerItem(
    val theme: TemplateThemePreference,
    val icon: ImageVector,
    @StringRes val titleId: Int
) {
    data object FollowSystem : ThemePickerItem(
        theme = TemplateThemePreference.FollowSystem,
        icon = TemplateIcons.Outlined.FollowSystem,
        titleId = R.string.system_default
    )

    data object Light : ThemePickerItem(
        theme = TemplateThemePreference.Light,
        icon = TemplateIcons.Outlined.LightMode,
        titleId = R.string.light_theme
    )

    data object Dark : ThemePickerItem(
        theme = TemplateThemePreference.Dark,
        icon = TemplateIcons.Outlined.DarkMode,
        titleId = R.string.dark_theme
    )
}

@PreviewLightDark
@Composable
private fun ThemePickerDialogPreview() {
    TemplateTheme {
        ThemePickerDialog(
            selectedTheme = TemplateThemePreference.Dark,
            onDismiss = { },
            onConfirm = { }
        )
    }
}
