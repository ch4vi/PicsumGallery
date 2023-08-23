package com.ch4vi.picsumgallery.presentation.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenKtTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun whenSortSectionOpenThenComponentIsVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                HomeContainer(
                    state = HomeState(
                        isSortSectionVisible = true
                    ),
                    snackbarHostState = SnackbarHostState(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("sort_section_tag").assertIsDisplayed()
        composeTestRule.onNodeWithTag("filter_section_tag").assertIsDisplayed()
    }

    @Test
    fun whenSortSectionClosedThenComponentIsNotVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                HomeContainer(
                    state = HomeState(
                        isSortSectionVisible = false
                    ),
                    snackbarHostState = SnackbarHostState(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("sort_section_tag").assertDoesNotExist()
        composeTestRule.onNodeWithTag("filter_section_tag").assertIsDisplayed()
    }
}
