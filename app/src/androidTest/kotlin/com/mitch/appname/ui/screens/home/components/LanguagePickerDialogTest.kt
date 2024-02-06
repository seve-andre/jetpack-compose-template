package com.mitch.appname.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.appname.domain.models.AppLanguage
import okhttp3.internal.toImmutableList
import org.junit.Rule
import org.junit.Test

class LanguagePickerDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val languagePickerItems = listOf(
        LanguagePickerItem.English,
        LanguagePickerItem.Italian
    ).toImmutableList()

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
        languagePickerItems.forEach { item ->
            composeTestRule.onNodeWithText(item.language.locale.displayLanguage).assertIsDisplayed()

            composeTestRule
                .onNodeWithTag(
                    testTag = item.flagId.toString(),
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

        val newOption = LanguagePickerItem.Italian

        // click on new one
        composeTestRule
            .onNodeWithText(newOption.language.locale.displayLanguage)
            .performClick()

        // old one is not selected
        composeTestRule
            .onNodeWithText(LanguagePickerItem.English.language.locale.displayLanguage)
            .assertIsNotSelected()

        // but new one is
        composeTestRule
            .onNodeWithText(newOption.language.locale.displayLanguage)
            .assertIsSelected()
    }
}
