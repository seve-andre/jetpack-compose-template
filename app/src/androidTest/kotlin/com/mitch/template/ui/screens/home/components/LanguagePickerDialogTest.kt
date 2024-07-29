package com.mitch.template.ui.screens.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.mitch.template.core.domain.models.TemplateLanguageConfig
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
        listOf(com.mitch.template.feature.home.components.LanguagePickerItem.English, com.mitch.template.feature.home.components.LanguagePickerItem.Italian).toImmutableList()
    )

    private val wrongItemsRobot = LanguagePickerRobot(
        composeTestRule,
        listOf(
            com.mitch.template.feature.home.components.LanguagePickerItem.English,
            com.mitch.template.feature.home.components.LanguagePickerItem.English,
            com.mitch.template.feature.home.components.LanguagePickerItem.Italian
        ).toImmutableList()
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            com.mitch.template.feature.home.components.LanguagePickerDialog(
                selectedLanguage = com.mitch.template.core.domain.models.TemplateLanguageConfig.Default,
                onDismiss = { },
                onConfirm = { }
            )
        }
    }

    @Test
    fun allLanguageOptionsExist() {
        with(correctItemsRobot) {
            assertLanguageExists(com.mitch.template.core.domain.models.TemplateLanguageConfig.English)
            assertLanguageExists(com.mitch.template.core.domain.models.TemplateLanguageConfig.Italian)
            assertLanguageIsSelected(com.mitch.template.core.domain.models.TemplateLanguageConfig.Default)
        }
    }

    @Test
    fun whenNewSelected_displaysCorrectOption() {
        with(correctItemsRobot) {
            assertLanguageIsSelected(com.mitch.template.core.domain.models.TemplateLanguageConfig.Default)
            selectLanguage(com.mitch.template.core.domain.models.TemplateLanguageConfig.Italian)
            assertLanguageIsSelected(com.mitch.template.core.domain.models.TemplateLanguageConfig.Italian)

            if (com.mitch.template.core.domain.models.TemplateLanguageConfig.Default != com.mitch.template.core.domain.models.TemplateLanguageConfig.Italian) {
                assertLanguageIsNotSelected(com.mitch.template.core.domain.models.TemplateLanguageConfig.Default)
            }
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenItemsAreWrong_throwsError() {
        with(wrongItemsRobot) {
            assertLanguageIsSelected(com.mitch.template.core.domain.models.TemplateLanguageConfig.Default)
            selectLanguage(com.mitch.template.core.domain.models.TemplateLanguageConfig.Italian)
            assertLanguageIsSelected(com.mitch.template.core.domain.models.TemplateLanguageConfig.Italian)

            if (com.mitch.template.core.domain.models.TemplateLanguageConfig.Default != com.mitch.template.core.domain.models.TemplateLanguageConfig.Italian) {
                assertLanguageIsNotSelected(com.mitch.template.core.domain.models.TemplateLanguageConfig.Default)
            }
        }
    }
}

class LanguagePickerRobot(
    private val composeTestRule: AppNameAndroidComposeTestRule,
    private val items: ImmutableList<com.mitch.template.feature.home.components.LanguagePickerItem>
) {

    fun selectLanguage(language: com.mitch.template.core.domain.models.TemplateLanguageConfig) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .performClick()
    }

    fun assertLanguageExists(language: com.mitch.template.core.domain.models.TemplateLanguageConfig) {
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

    fun assertLanguageIsSelected(language: com.mitch.template.core.domain.models.TemplateLanguageConfig) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsSelected()
    }

    fun assertLanguageIsNotSelected(language: com.mitch.template.core.domain.models.TemplateLanguageConfig) {
        composeTestRule
            .onNodeWithText(language.locale.displayLanguage)
            .assertIsNotSelected()
    }
}
