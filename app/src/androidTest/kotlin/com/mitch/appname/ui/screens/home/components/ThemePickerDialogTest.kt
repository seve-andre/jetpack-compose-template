package com.mitch.appname.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.appname.domain.models.AppTheme
import okhttp3.internal.toImmutableList
import org.junit.Rule
import org.junit.Test

class ThemePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(@StringRes id: Int) = composeTestRule.activity.resources.getString(id)

    private val themePickerItems = listOf(
        ThemePickerItem.FollowSystem,
        ThemePickerItem.Light,
        ThemePickerItem.Dark
    ).toImmutableList()

    @Test
    fun showsAllThemeOptionsAreDisplayed() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = AppTheme.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        // all themes are displayed
        themePickerItems.forEach { item ->
            composeTestRule.onNodeWithText(getString(item.titleId)).assertIsDisplayed()
        }

        // default theme is selected
        composeTestRule
            .onNodeWithText(getString(ThemePickerItem.FollowSystem.titleId))
            .assertIsSelected()
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = AppTheme.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        val newOption = ThemePickerItem.Dark

        // click on new one
        composeTestRule
            .onNodeWithText(getString(newOption.titleId))
            .performClick()

        // old one is not selected
        composeTestRule
            .onNodeWithText(getString(ThemePickerItem.FollowSystem.titleId))
            .assertIsNotSelected()

        // but new one is
        composeTestRule
            .onNodeWithText(getString(newOption.titleId))
            .assertIsSelected()
    }
}
