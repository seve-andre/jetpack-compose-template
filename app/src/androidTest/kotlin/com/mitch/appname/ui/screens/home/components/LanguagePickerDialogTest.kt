package com.mitch.appname.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.mitch.appname.util.AppLanguage
import org.junit.Rule
import org.junit.Test

class LanguagePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun test_showsAllLanguageOptionsAreDisplayed() {
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
}
