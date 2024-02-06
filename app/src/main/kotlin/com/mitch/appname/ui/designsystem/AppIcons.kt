package com.mitch.appname.ui.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Translate
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.fill.AlertTriangle
import compose.icons.evaicons.fill.CheckmarkCircle2
import compose.icons.evaicons.fill.CloseCircle

object AppIcons {
    object Filled {
        val Success = EvaIcons.Fill.CheckmarkCircle2
        val Warning = EvaIcons.Fill.AlertTriangle
        val Error = EvaIcons.Fill.CloseCircle
    }

    object Outlined {
        val LightMode = Icons.Outlined.LightMode
        val DarkMode = Icons.Outlined.DarkMode
        val FollowSystem = Icons.Outlined.Contrast
        val Translate = Icons.Outlined.Translate
        val Palette = Icons.Outlined.Palette
    }
}
