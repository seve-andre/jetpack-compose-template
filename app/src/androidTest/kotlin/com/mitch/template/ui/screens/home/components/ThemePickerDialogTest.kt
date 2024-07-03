package com.mitch.template.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.template.domain.models.TemplateThemeConfig
import com.mitch.template.ui.util.AppNameAndroidComposeTestRule
import com.mitch.template.ui.util.stringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
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
        ).toImmutableList()
    )

    private val wrongItemsRobot = ThemePickerRobot(
        composeTestRule,
        listOf(
            ThemePickerItem.FollowSystem,
            ThemePickerItem.FollowSystem, // repeated twice -> wrong
            ThemePickerItem.Light
        ).toImmutableList()
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = TemplateThemeConfig.Default,
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allThemeOptionsExist() {
        with(correctItemsRobot) {
            assertThemeExists(TemplateThemeConfig.FollowSystem)
            assertThemeExists(TemplateThemeConfig.Light)
            assertThemeExists(TemplateThemeConfig.Dark)
            assertThemeIsSelected(TemplateThemeConfig.Default)
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(correctItemsRobot) {
            selectTheme(TemplateThemeConfig.Dark)
            assertThemeIsNotSelected(TemplateThemeConfig.Light)
            assertThemeIsNotSelected(TemplateThemeConfig.FollowSystem)
            assertThemeIsSelected(TemplateThemeConfig.Dark)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenItemsAreWrong_throwsError() {
        with(wrongItemsRobot) {
            selectTheme(TemplateThemeConfig.Dark)
            assertThemeIsNotSelected(TemplateThemeConfig.Light)
            assertThemeIsNotSelected(TemplateThemeConfig.FollowSystem)
            assertThemeIsSelected(TemplateThemeConfig.Dark)
        }
    }
}

class ThemePickerRobot(
    private val composeTestRule: AppNameAndroidComposeTestRule,
    private val items: ImmutableList<ThemePickerItem>
) {
    fun selectTheme(theme: TemplateThemeConfig) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .performClick()
    }

    fun assertThemeExists(theme: TemplateThemeConfig) {
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

    fun assertThemeIsSelected(theme: TemplateThemeConfig) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsSelected()
    }

    fun assertThemeIsNotSelected(theme: TemplateThemeConfig) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsNotSelected()
    }
}
