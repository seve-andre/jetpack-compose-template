package com.mitch.template.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

/**
 * Returns a [ViewModelProvider.Factory] which will return the result of [create] when it's
 * [ViewModelProvider.Factory.create] function is called.
 *
 * If the created [ViewModel] does not match the requested class, an [IllegalArgumentException]
 * exception is thrown.
 */
inline fun <reified VM : ViewModel> viewModelProviderFactory(
    crossinline create: () -> VM
): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        create()
    }
}
