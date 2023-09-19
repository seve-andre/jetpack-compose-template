package com.mitch.appname.ui.screen.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.appname.util.AppLanguage
import org.junit.Rule
import org.junit.Test

class LanguagePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsAllLanguageOptionsAreDisplayed() {
        composeTestRule.setContent {
            LanguagePickerDialog(
                selectedLanguage = AppLanguage.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        // all languages and their flags are displayed
        AppLanguage.values().forEach { language ->
            composeTestRule.onNodeWithText(language.locale.displayLanguage).assertIsDisplayed()

            composeTestRule
                .onNodeWithTag(
                    testTag = language.flagId.toString(),
                    useUnmergedTree = true
                )
                .assertIsDisplayed()
        }

        // default language is selected
        composeTestRule
            .onNodeWithText(AppLanguage.default().locale.displayLanguage)
            .assertIsSelected()
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        composeTestRule.setContent {
            LanguagePickerDialog(
                selectedLanguage = AppLanguage.default(),
                onDismiss = { },
                onConfirm = { }
            )
        }

        val newOption = AppLanguage.values().filter { it != AppLanguage.default() }[0]

        // click on new one
        composeTestRule
            .onNodeWithText(newOption.locale.displayLanguage)
            .performClick()

        // old one is not selected
        composeTestRule
            .onNodeWithText(AppLanguage.default().locale.displayLanguage)
            .assertIsNotSelected()

        // but new one is
        composeTestRule
            .onNodeWithText(newOption.locale.displayLanguage)
            .assertIsSelected()
    }
}
