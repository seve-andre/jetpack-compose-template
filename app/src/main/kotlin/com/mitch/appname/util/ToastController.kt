package com.mitch.appname.util

import android.content.Context
import android.widget.Toast

object ToastController {

    private var showingToast: Toast? = null
    fun show(
        context: Context,
        message: String,
        duration: ToastDuration = ToastDuration.SHORT
    ) {
        if (showingToast != null) {
            showingToast?.cancel()
        }
        showingToast = Toast.makeText(
            context,
            message,
            when (duration) {
                ToastDuration.SHORT -> Toast.LENGTH_SHORT
                ToastDuration.LONG -> Toast.LENGTH_LONG
            }
        )
        showingToast?.show()
    }
}

enum class ToastDuration {
    SHORT,
    LONG
}
