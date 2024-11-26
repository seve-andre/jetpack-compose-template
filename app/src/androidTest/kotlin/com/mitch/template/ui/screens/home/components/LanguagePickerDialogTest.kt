package com.mitch.template.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.template.domain.models.TemplateLanguagePreference
import com.mitch.template.ui.util.AppNameAndroidComposeTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LanguagePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val correctItemsRobot = LanguagePickerRobot(
        composeTestRule,
        listOf(LanguagePickerItem.English, LanguagePickerItem.Italian)
    )

    private val wrongItemsRobot = LanguagePickerRobot(
        composeTestRule,
        listOf(
            LanguagePickerItem.English,
            LanguagePickerItem.English,
            LanguagePickerItem.Italian
        )
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            LanguagePickerDialog(
                selectedLanguage = TemplateLanguagePreference.Default,
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allLanguageOptionsExist() {
        with(correctItemsRobot) {
            assertLanguageExists(TemplateLanguagePreference.English)
            assertLanguageExists(TemplateLanguagePreference.Italian)
            assertLanguageIsSelected(TemplateLanguagePreference.Default)
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(correctItemsRobot) {
            assertLanguageIsSelected(TemplateLanguagePreference.Default)
            selectLanguage(TemplateLanguagePreference.Italian)
            assertLanguageIsSelected(TemplateLanguagePreference.Italian)

            if (TemplateLanguagePreference.Default != TemplateLanguagePreference.Italian) {
                assertLanguageIsNotSelected(TemplateLanguagePreference.Default)
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenItemsAreWrong_throwsError() {
        with(wrongItemsRobot) {
            assertLanguageIsSelected(TemplateLanguagePreference.Default)
            selectLanguage(TemplateLanguagePreference.Italian)
            assertLanguageIsSelected(TemplateLanguagePreference.Italian)

            if (TemplateLanguagePreference.Default != TemplateLanguagePreference.Italian) {
                assertLanguageIsNotSelected(TemplateLanguagePreference.Default)
            }
        }
    }
}

class LanguagePickerRobot(
    private val composeTestRule: AppNameAndroidComposeTestRule,
    private val items: List<LanguagePickerItem>
) {

    fun selectLanguage(language: TemplateLanguagePreference) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .performClick()
    }

    fun assertLanguageExists(language: TemplateLanguagePreference) {
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

    fun assertLanguageIsSelected(language: TemplateLanguagePreference) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsSelected()
    }

    fun assertLanguageIsNotSelected(language: TemplateLanguagePreference) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsNotSelected()
    }
}
