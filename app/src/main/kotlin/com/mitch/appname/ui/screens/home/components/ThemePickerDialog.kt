package com.mitch.appname.ui.screens.home.components

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.mitch.appname.R
import com.mitch.appname.domain.models.AppTheme
import com.mitch.appname.ui.designsystem.AppIcons
import com.mitch.appname.ui.designsystem.theme.custom.padding
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ThemePickerDialog(
    selectedTheme: AppTheme,
    onDismiss: () -> Unit,
    onConfirm: (AppTheme) -> Unit
) {
    var tempTheme by remember { mutableStateOf(selectedTheme) }

    val items = listOf(
        ThemePickerItem.FollowSystem,
        ThemePickerItem.Light,
        ThemePickerItem.Dark
    ).toImmutableList()

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = AppIcons.Outlined.Palette,
                contentDescription = null
            )
        },
        title = {
            Text(text = stringResource(id = R.string.change_theme))
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
                                selected = (item.theme == tempTheme),
                                onClick = { tempTheme = item.theme },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = padding.medium),
                        horizontalArrangement = Arrangement.spacedBy(padding.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (item.theme == tempTheme),
                            onClick = null // null recommended for accessibility with screen readers
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(padding.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = stringResource(id = item.titleId),
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
    val theme: AppTheme,
    val icon: ImageVector,
    @StringRes val titleId: Int
) {
    data object FollowSystem : ThemePickerItem(
        theme = AppTheme.FollowSystem,
        icon = AppIcons.Outlined.FollowSystem,
        titleId = R.string.system_default
    )

    data object Light : ThemePickerItem(
        theme = AppTheme.Light,
        icon = AppIcons.Outlined.LightMode,
        titleId = R.string.light_theme
    )

    data object Dark : ThemePickerItem(
        theme = AppTheme.Dark,
        icon = AppIcons.Outlined.DarkMode,
        titleId = R.string.dark_theme
    )
}
