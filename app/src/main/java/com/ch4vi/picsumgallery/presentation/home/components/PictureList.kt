package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.presentation.home.HomeState
import com.ch4vi.picsumgallery.presentation.home.homeStatePreview
import com.ch4vi.picsumgallery.presentation.util.MultiPreview
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme

private const val LOADING_TEST_TAG = "loading_tag"

@Composable
fun PictureList(
    modifier: Modifier = Modifier,
    state: HomeState,
    onBottomReached: () -> Unit
) {
    val listState = rememberLazyListState()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Row(
                modifier = Modifier
                    .testTag(LOADING_TEST_TAG)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.loading))
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.list) { item ->
                PictureItem(
                    item = item,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    listState.OnBottomReached(onBottomReached)
}

@MultiPreview
@Composable
fun PictureListPreview() {
    PicsumGalleryTheme {
        PictureList(state = homeStatePreview) {}
    }
}
