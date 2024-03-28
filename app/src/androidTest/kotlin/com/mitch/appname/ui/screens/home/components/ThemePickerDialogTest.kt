package com.mitch.appname.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.appname.domain.models.AppTheme
import com.mitch.appname.ui.util.AppNameAndroidComposeTestRule
import com.mitch.appname.ui.util.stringResource
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ThemePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = AppTheme.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allThemeOptionsExist() {
        with(ThemePickerRobot(composeTestRule)) {
            assertThemeExists(AppTheme.FollowSystem)
            assertThemeExists(AppTheme.Light)
            assertThemeExists(AppTheme.Dark)
            assertThemeIsSelected(AppTheme.default())
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(ThemePickerRobot(composeTestRule)) {
            selectTheme(AppTheme.Dark)
            assertThemeIsNotSelected(AppTheme.Light)
            assertThemeIsNotSelected(AppTheme.FollowSystem)
            assertThemeIsSelected(AppTheme.Dark)
        }
    }
}

class ThemePickerRobot(private val composeTestRule: AppNameAndroidComposeTestRule) {
    private val themePickerItems = listOf(
        ThemePickerItem.FollowSystem, ThemePickerItem.Light, ThemePickerItem.Dark
    )

    fun selectTheme(theme: AppTheme) {
        val item = themePickerItems.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .performClick()
    }

    fun assertThemeExists(theme: AppTheme) {
        val item = themePickerItems.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertExists()

        composeTestRule
            .onNodeWithTag(
                testTag = item.icon.toString(),
                useUnmergedTree = true
            )
            .assertExists()
    }

    fun assertThemeIsSelected(theme: AppTheme) {
        val item = themePickerItems.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsSelected()
    }

    fun assertThemeIsNotSelected(theme: AppTheme) {
        val item = themePickerItems.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsNotSelected()
    }
}
