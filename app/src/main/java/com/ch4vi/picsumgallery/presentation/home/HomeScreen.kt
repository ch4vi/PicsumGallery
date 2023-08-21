package com.ch4vi.picsumgallery.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme

@Composable
fun HomeScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PicsumGalleryTheme {
        HomeScreen("Android")
    }
}
