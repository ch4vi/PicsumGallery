package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.ch4vi.picsumgallery.presentation.home.HomeState
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme
import org.junit.Rule
import org.junit.Test

class HomeToolbarKtTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun whenOfflineThenBannerIsVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                HomeToolbar(
                    state = HomeState(
                        isOfflineVisible = true
                    ),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("offline_tag").assertIsDisplayed()
        composeTestRule.onNodeWithTag("title_tag").assertIsDisplayed()
        composeTestRule.onNodeWithTag("filter_tag").assertIsDisplayed()
    }

    @Test
    fun whenOnlineThenBannerIsNotVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                HomeToolbar(
                    state = HomeState(
                        isOfflineVisible = false
                    ),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("offline_tag").assertDoesNotExist()
        composeTestRule.onNodeWithTag("title_tag").assertIsDisplayed()
        composeTestRule.onNodeWithTag("filter_tag").assertIsDisplayed()
    }
}
