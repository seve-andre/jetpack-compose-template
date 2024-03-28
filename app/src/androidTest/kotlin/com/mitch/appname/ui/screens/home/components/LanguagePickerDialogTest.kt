package com.mitch.appname.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.appname.domain.models.AppLanguage
import com.mitch.appname.ui.util.AppNameAndroidComposeTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LanguagePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            LanguagePickerDialog(
                selectedLanguage = AppLanguage.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allLanguageOptionsExist() {
        with(LanguagePickerRobot(composeTestRule)) {
            assertLanguageExists(AppLanguage.English)
            assertLanguageExists(AppLanguage.Italian)
            assertLanguageIsSelected(AppLanguage.default())
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(LanguagePickerRobot(composeTestRule)) {
            assertLanguageIsSelected(AppLanguage.default())
            selectLanguage(AppLanguage.Italian)
            assertLanguageIsSelected(AppLanguage.Italian)

            if (AppLanguage.default() != AppLanguage.Italian) {
                assertLanguageIsNotSelected(AppLanguage.default())
            }
        }
    }
}

class LanguagePickerRobot(private val composeTestRule: AppNameAndroidComposeTestRule) {
    private val languagePickerItems = listOf(LanguagePickerItem.English, LanguagePickerItem.Italian)

    fun selectLanguage(language: AppLanguage) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .performClick()
    }

    fun assertLanguageExists(language: AppLanguage) {
        val item = languagePickerItems.singleOrNull { it.language == language }
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

    fun assertLanguageIsSelected(language: AppLanguage) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsSelected()
    }

    fun assertLanguageIsNotSelected(language: AppLanguage) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsNotSelected()
    }
}
