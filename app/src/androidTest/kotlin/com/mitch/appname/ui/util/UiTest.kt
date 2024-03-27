package com.mitch.appname.ui.util

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule

typealias AppNameAndroidComposeTestRule = AndroidComposeTestRule<*, *>
fun AppNameAndroidComposeTestRule.stringResource(
    @StringRes id: Int,
    vararg formatArgs: Any
): String = this.activity.getString(id, formatArgs)
