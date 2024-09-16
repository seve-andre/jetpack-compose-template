package com.mitch.template.feature.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.template.feature.home.components.ThemePickerItem.Dark
import com.mitch.template.feature.home.components.ThemePickerItem.FollowSystem
import com.mitch.template.feature.home.components.ThemePickerItem.Light
import com.mitch.template.feature.util.AppNameAndroidComposeTestRule
import com.mitch.template.feature.util.stringResource
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
            FollowSystem,
            Dark,
            Light
        ).toImmutableList()
    )

    private val wrongItemsRobot = ThemePickerRobot(
        composeTestRule,
        listOf(
            FollowSystem,
            FollowSystem, // repeated twice -> wrong
            Light
        ).toImmutableList()
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ThemePickerDialog(
                selectedTheme = com.mitch.template.core.domain.models.TemplateThemeConfig.Default,
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allThemeOptionsExist() {
        with(correctItemsRobot) {
            assertThemeExists(com.mitch.template.core.domain.models.TemplateThemeConfig.FollowSystem)
            assertThemeExists(com.mitch.template.core.domain.models.TemplateThemeConfig.Light)
            assertThemeExists(com.mitch.template.core.domain.models.TemplateThemeConfig.Dark)
            assertThemeIsSelected(com.mitch.template.core.domain.models.TemplateThemeConfig.Default)
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(correctItemsRobot) {
            selectTheme(com.mitch.template.core.domain.models.TemplateThemeConfig.Dark)
            assertThemeIsNotSelected(com.mitch.template.core.domain.models.TemplateThemeConfig.Light)
            assertThemeIsNotSelected(com.mitch.template.core.domain.models.TemplateThemeConfig.FollowSystem)
            assertThemeIsSelected(com.mitch.template.core.domain.models.TemplateThemeConfig.Dark)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenItemsAreWrong_throwsError() {
        with(wrongItemsRobot) {
            selectTheme(com.mitch.template.core.domain.models.TemplateThemeConfig.Dark)
            assertThemeIsNotSelected(com.mitch.template.core.domain.models.TemplateThemeConfig.Light)
            assertThemeIsNotSelected(com.mitch.template.core.domain.models.TemplateThemeConfig.FollowSystem)
            assertThemeIsSelected(com.mitch.template.core.domain.models.TemplateThemeConfig.Dark)
        }
    }
}

class ThemePickerRobot(
    private val composeTestRule: AppNameAndroidComposeTestRule,
    private val items: ImmutableList<ThemePickerItem>
) {
    fun selectTheme(theme: com.mitch.template.core.domain.models.TemplateThemeConfig) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .performClick()
    }

    fun assertThemeExists(theme: com.mitch.template.core.domain.models.TemplateThemeConfig) {
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

    fun assertThemeIsSelected(theme: com.mitch.template.core.domain.models.TemplateThemeConfig) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsSelected()
    }

    fun assertThemeIsNotSelected(theme: com.mitch.template.core.domain.models.TemplateThemeConfig) {
        val item = items.singleOrNull { it.theme == theme }
        requireNotNull(item) {
            "item from theme $theme is null; check that items DO NOT have the same theme"
        }

        composeTestRule
            .onNodeWithText(composeTestRule.stringResource(id = item.titleId))
            .assertIsNotSelected()
    }
}
