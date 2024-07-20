package com.mitch.template.lint.util

import org.jetbrains.uast.UAnnotated

val UAnnotated.isComposable: Boolean
    get() = this.findAnnotation("androidx.compose.runtime.Composable") != null
