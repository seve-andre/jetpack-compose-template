package com.mitch.template.core.ui

import com.mitch.template.core.designsystem.components.snackbars.TemplateSnackbarVisuals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * Class responsible for managing Snackbar messages to show on the screen
 */
object SnackbarManager {

    private val _messages: MutableStateFlow<List<SnackbarMessage>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<SnackbarMessage>> get() = _messages.asStateFlow()

    fun show(
        visuals: TemplateSnackbarVisuals,
        onActionPerform: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null
    ) {
        _messages.update { currentMessages ->
            currentMessages + SnackbarMessage(
                id = UUID.randomUUID().mostSignificantBits,
                visuals = visuals,
                onActionPerform = onActionPerform,
                onDismiss = onDismiss
            )
        }
    }

    fun setMessageShown(messageId: Long) {
        _messages.update { currentMessages ->
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
