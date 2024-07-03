package com.mitch.template.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.template.domain.models.TemplateLanguageConfig
import com.mitch.template.ui.util.AppNameAndroidComposeTestRule
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LanguagePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val correctItemsRobot = LanguagePickerRobot(
        composeTestRule,
        listOf(LanguagePickerItem.English, LanguagePickerItem.Italian).toImmutableList()
    )

    private val wrongItemsRobot = LanguagePickerRobot(
        composeTestRule,
        listOf(
            LanguagePickerItem.English,
            LanguagePickerItem.English,
            LanguagePickerItem.Italian
        ).toImmutableList()
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            LanguagePickerDialog(
                selectedLanguage = TemplateLanguageConfig.Default,
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allLanguageOptionsExist() {
        with(correctItemsRobot) {
            assertLanguageExists(TemplateLanguageConfig.English)
            assertLanguageExists(TemplateLanguageConfig.Italian)
            assertLanguageIsSelected(TemplateLanguageConfig.Default)
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(correctItemsRobot) {
            assertLanguageIsSelected(TemplateLanguageConfig.Default)
            selectLanguage(TemplateLanguageConfig.Italian)
            assertLanguageIsSelected(TemplateLanguageConfig.Italian)

            if (TemplateLanguageConfig.Default != TemplateLanguageConfig.Italian) {
                assertLanguageIsNotSelected(TemplateLanguageConfig.Default)
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenItemsAreWrong_throwsError() {
        with(wrongItemsRobot) {
            assertLanguageIsSelected(TemplateLanguageConfig.Default)
            selectLanguage(TemplateLanguageConfig.Italian)
            assertLanguageIsSelected(TemplateLanguageConfig.Italian)

            if (TemplateLanguageConfig.Default != TemplateLanguageConfig.Italian) {
                assertLanguageIsNotSelected(TemplateLanguageConfig.Default)
            }
        }
    }
}

class LanguagePickerRobot(
    private val composeTestRule: AppNameAndroidComposeTestRule,
    private val items: ImmutableList<LanguagePickerItem>
) {

    fun selectLanguage(language: TemplateLanguageConfig) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .performClick()
    }

    fun assertLanguageExists(language: TemplateLanguageConfig) {
        val item = items.singleOrNull { it.language == language }
        requireNotNull(item) {
            "item from language $language is null; check that items DO NOT have the same language"
        }

        composeTestRule
            .onNodeWithText(item.language.locale.displayLanguage)
            .assertExists()

        composeTestRule
            .onNodeWithTag(
                testTag = item.flagId.toString(),
                useUnmergedTree = true
            )
            .assertExists()
    }

    fun assertLanguageIsSelected(language: TemplateLanguageConfig) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsSelected()
    }

    fun assertLanguageIsNotSelected(language: TemplateLanguageConfig) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsNotSelected()
    }
}
