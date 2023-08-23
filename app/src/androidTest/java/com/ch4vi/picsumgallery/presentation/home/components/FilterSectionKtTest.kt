package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme
import org.junit.Rule
import org.junit.Test

class FilterSectionKtTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun whenDropDownExpandedOtherAuthorsAreVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                FilterSection(
                    selectedOption = "foo",
                    options = listOf("foo", "bar"),
                    isExpanded = true,
                    onExpandedChange = {},
                    onOptionSelected = {},
                    onClear = {}
                )
            }
        }
        composeTestRule.onNodeWithText("bar").assertIsDisplayed()
    }

    @Test
    fun whenDropDownExpandedOtherAuthorsAreNotVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                FilterSection(
                    selectedOption = "foo",
                    options = listOf("foo", "bar"),
                    isExpanded = false,
                    onExpandedChange = {},
                    onOptionSelected = {},
                    onClear = {}
                )
            }
        }
        composeTestRule.onNodeWithText("bar").assertDoesNotExist()
    }
}
