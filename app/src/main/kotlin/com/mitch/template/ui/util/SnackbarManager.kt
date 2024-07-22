package com.mitch.template.ui.util

import com.mitch.template.ui.designsystem.components.snackbars.TemplateSnackbarVisuals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * Class responsible for managing Snackbar messages to show on the screen
 */
object SnackbarManager {

    private val _Messages: MutableStateFlow<List<SnackbarMessage>> = MutableStateFlow(emptyList())
    val Messages: StateFlow<List<SnackbarMessage>> get() = _Messages.asStateFlow()

    fun show(
        visuals: TemplateSnackbarVisuals,
        onActionPerform: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null
    ) {
        _Messages.update { currentMessages ->
            currentMessages + SnackbarMessage(
                id = UUID.randomUUID().mostSignificantBits,
                visuals = visuals,
                onActionPerform = onActionPerform,
                onDismiss = onDismiss
            )
        }
    }

    fun setMessageShown(messageId: Long) {
        _Messages.update { currentMessages ->
            currentMessages.filterNot { it.id == messageId }
        }
    }
}

data class SnackbarMessage(
    val id: Long,
    val visuals: TemplateSnackbarVisuals,
    val onActionPerform: (() -> Unit)?,
    val onDismiss: (() -> Unit)?
)
