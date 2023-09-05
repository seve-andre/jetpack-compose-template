package com.mitch.appname.ui.screens.home

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.mitch.appname.R
import com.mitch.appname.ui.util.components.loading.LoadingTag
import com.mitch.appname.util.AppLanguage
import com.mitch.appname.util.AppTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun getString(@StringRes id: Int) = composeTestRule.activity.resources.getString(id)

    @Test
    fun loading_showsLoadingScreen() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = HomeUiState.Loading,
                onChangeLanguage = { },
                onChangeTheme = { }
            )
        }

        composeTestRule
            .onNodeWithTag(LoadingTag)
            .assertIsDisplayed()
    }

    @Test
    fun success_showsSettingsOptions() {
        composeTestRule.setContent {
            HomeScreen(
                uiState = HomeUiState.Success(
                    language = AppLanguage.ENGLISH,
                    theme = AppTheme.FOLLOW_SYSTEM
                ),
                onChangeLanguage = { },
                onChangeTheme = { }
            )
        }

        composeTestRule.onNodeWithText(getString(R.string.change_language))
        composeTestRule.onNodeWithText(getString(R.string.change_theme))
    }
}
