package com.mitch.template.core.designsystem.components.snackbars

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mitch.template.core.designsystem.R
import com.mitch.template.core.designsystem.TemplateDesignSystem
import com.mitch.template.core.designsystem.TemplateIcons
import com.mitch.template.core.designsystem.custom.padding

@Composable
fun DismissibleSnackbar(snackbarHostState: SnackbarHostState) {
    val dismissSnackbarState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value != SwipeToDismissBoxValue.Settled) {
                snackbarHostState.currentSnackbarData?.dismiss()
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(dismissSnackbarState.currentValue) {
        if (dismissSnackbarState.currentValue != SwipeToDismissBoxValue.Settled) {
            dismissSnackbarState.reset()
        }
    }

    SwipeToDismissBox(
        state = dismissSnackbarState,
        backgroundContent = { },
        content = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = padding.medium)
            ) { snackbarData ->
                val customVisuals = snackbarData.visuals as TemplateSnackbarVisuals

                val colors = when (customVisuals.type) {
                    TemplateSnackbarType.Default -> TemplateSnackbarDefaults.defaultSnackbarColors()
                    TemplateSnackbarType.Success -> TemplateSnackbarDefaults.successSnackbarColors()
                    TemplateSnackbarType.Warning -> TemplateSnackbarDefaults.warningSnackbarColors()
                    TemplateSnackbarType.Error -> TemplateSnackbarDefaults.errorSnackbarColors()
                }

                TemplateSnackbar(
                    colors = colors,
                    icon = customVisuals.imageVector,
                    message = customVisuals.message,
                    action = customVisuals.actionLabel?.let {
                        {
                            TextButton(onClick = snackbarData::performAction) {
                                Text(text = customVisuals.actionLabel)
                            }
                        }
                    },
                    dismissAction = if (customVisuals.duration == Indefinite) {
                        {
                            IconButton(
                                onClick = snackbarData::dismiss,
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = TemplateDesignSystem.colorScheme.inverseOnSurface
                                )
                            ) {
                                Icon(
                                    imageVector = TemplateIcons.Outlined.Close,
                                    contentDescription = stringResource(
                                        id = R.string.dismiss_snackbar_content_description
                                    )
                                )
                            }
                        }
                    } else {
                        null
                    }
                )
            }
        }
    )
}
