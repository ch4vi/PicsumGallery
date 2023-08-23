package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme
import org.junit.Rule
import org.junit.Test

class ImageItemKtTest {

    @get:Rule
    var composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun whenAuthorThenDisplayName() {
        composeTestRule.setContent {
            PicsumGalleryTheme {
                PictureItem(
                    item = Picture(
                        id = 1,
                        author = "foo",
                        width = 1,
                        height = 1,
                        url = "url",
                        downloadUrl = "url"
                    )
                )
            }
        }
        composeTestRule.onNodeWithText("foo").assertIsDisplayed()
    }
}
