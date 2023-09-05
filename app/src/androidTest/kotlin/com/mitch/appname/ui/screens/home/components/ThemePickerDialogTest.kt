package com.mitch.appname.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.mitch.appname.util.AppTheme
import org.junit.Rule
import org.junit.Test

class ThemePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(@StringRes id: Int) = composeTestRule.activity.resources.getString(id)

    @Test
    fun test_showsAllThemeOptionsAreDisplayed() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = AppTheme.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        // all themes are displayed
        AppTheme.values().forEach { theme ->
            composeTestRule.onNodeWithText(getString(theme.translationId)).assertIsDisplayed()
        }

        // default theme is selected
        composeTestRule
            .onNodeWithText(getString(AppTheme.default().translationId))
            .assertIsSelected()
    }
}
