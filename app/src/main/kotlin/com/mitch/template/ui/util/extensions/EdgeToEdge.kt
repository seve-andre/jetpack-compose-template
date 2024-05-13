package com.mitch.template.ui.util.extensions

import android.graphics.Color
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView

@Composable
fun SetSystemBarsColor(color: SystemBarsColor) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as? ComponentActivity
            activity?.let {
                when (color) {
                    is SystemBarsColor.Both -> {
                        it.enableEdgeToEdge(
                            statusBarStyle = if (color.statusDarkIcons) {
                                SystemBarStyle.light(
                                    scrim = Color.TRANSPARENT,
                                    darkScrim = Color.TRANSPARENT
                                )
                            } else {
                                SystemBarStyle.dark(scrim = Color.TRANSPARENT)
                            },
                            navigationBarStyle = if (color.navigationDarkIcons) {
                                SystemBarStyle.light(
                                    scrim = Color.TRANSPARENT,
                                    darkScrim = Color.TRANSPARENT
                                )
                            } else {
                                SystemBarStyle.dark(scrim = Color.TRANSPARENT)
                            }
                        )
                    }

                    is SystemBarsColor.NavigationBar -> {
                        if (color.darkIcons) {
                            it.enableEdgeToEdge(
                                navigationBarStyle = SystemBarStyle.light(
                                    scrim = Color.TRANSPARENT,
                                    darkScrim = Color.TRANSPARENT
                                )
                            )
                        } else {
                            it.enableEdgeToEdge(navigationBarStyle = SystemBarStyle.dark(scrim = Color.TRANSPARENT))
                        }
                    }

                    is SystemBarsColor.StatusBar -> {
                        if (color.darkIcons) {
                            it.enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.light(
                                    scrim = Color.TRANSPARENT,
                                    darkScrim = Color.TRANSPARENT
                                )
                            )
                        } else {
                            it.enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(scrim = Color.TRANSPARENT))
                        }
                    }
                }
            }
        }
    }
}

sealed interface SystemBarsColor {
    data class Both(val statusDarkIcons: Boolean, val navigationDarkIcons: Boolean) : SystemBarsColor
    data class StatusBar(val darkIcons: Boolean) : SystemBarsColor
    data class NavigationBar(val darkIcons: Boolean) : SystemBarsColor
}
