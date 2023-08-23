package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.presentation.home.HomeState
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme
import org.junit.Rule
import org.junit.Test

class PictureListKtTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun whenLoadingThenProgressIsVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                PictureList(
                    state = HomeState(
                        isLoading = true
                    ),
                    onBottomReached = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("loading_tag").assertIsDisplayed()
    }

    @Test
    fun whenPicturesThenListItemsAreVisible() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                PictureList(
                    state = HomeState(
                        list = listOf(
                            Picture(
                                1,
                                "foo",
                                1,
                                1,
                                "url",
                                "url"
                            ),
                            Picture(2, "bar", 1, 1, "url", "url")
                        )
                    ),
                    onBottomReached = {}
                )
            }
        }
        composeTestRule.onNodeWithText("foo").assertIsDisplayed()
        composeTestRule.onNodeWithText("bar").assertIsDisplayed()
    }
}
