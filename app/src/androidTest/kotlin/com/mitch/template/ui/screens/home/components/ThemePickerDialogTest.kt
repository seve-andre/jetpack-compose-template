package com.mitch.template.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.template.domain.models.TemplateThemePreference
import com.mitch.template.ui.util.AppNameAndroidComposeTestRule
import com.mitch.template.ui.util.stringResource
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ThemePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val correctItemsRobot = ThemePickerRobot(
        composeTestRule,
        listOf(
            ThemePickerItem.FollowSystem,
            ThemePickerItem.Dark,
            ThemePickerItem.Light
        )
    )

    private val wrongItemsRobot = ThemePickerRobot(
        composeTestRule,
        listOf(
            ThemePickerItem.FollowSystem,
            ThemePickerItem.FollowSystem, // repeated twice -> wrong
            ThemePickerItem.Light
        )
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = TemplateThemePreference.Default,
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allThemeOptionsExist() {
        with(correctItemsRobot) {
            assertThemeExists(TemplateThemePreference.FollowSystem)
            assertThemeExists(TemplateThemePreference.Light)
            assertThemeExists(TemplateThemePreference.Dark)
            assertThemeIsSelected(TemplateThemePreference.Default)
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(correctItemsRobot) {
            selectTheme(TemplateThemePreference.Dark)
            assertThemeIsNotSelected(TemplateThemePreference.Light)
            assertThemeIsNotSelected(TemplateThemePreference.FollowSystem)
            assertThemeIsSelected(TemplateThemePreference.Dark)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenItemsAreWrong_throwsError() {
        with(wrongItemsRobot) {
            selectTheme(TemplateThemePreference.Dark)
            assertThemeIsNotSelected(TemplateThemePreference.Light)
            assertThemeIsNotSelected(TemplateThemePreference.FollowSystem)
            assertThemeIsSelected(TemplateThemePreference.Dark)
        }
    }
}

class ThemePickerRobot(
    private val composeTestRule: AppNameAndroidComposeTestRule,
    private val items: List<ThemePickerItem>
) {
    fun selectTheme(theme: TemplateThemePreference) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .performClick()
    }

    fun assertThemeExists(theme: TemplateThemePreference) {
        val item = items.singleOrNull { it.theme == theme }
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

    fun assertThemeIsSelected(theme: TemplateThemePreference) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsSelected()
    }

    fun assertThemeIsNotSelected(theme: TemplateThemePreference) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsNotSelected()
    }
}
