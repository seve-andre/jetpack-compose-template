package com.mitch.template.core.ui

import com.mitch.template.core.designsystem.components.snackbars.SnackbarEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Class responsible for managing Snackbar events to show on the screen
 */
object SnackbarManager {

    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun show(event: SnackbarEvent) = _events.send(event)
}
