package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.presentation.home.HomeEvent
import com.ch4vi.picsumgallery.presentation.home.HomeState
import com.ch4vi.picsumgallery.presentation.home.homeStatePreview
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme

@Composable
fun HomeToolbar(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Column {
        AnimatedVisibility(
            visible = state.isOfflineVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.offline),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
            IconButton(
                onClick = { onEvent(HomeEvent.ToggleSortingVisibility) }
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = stringResource(R.string.description_icon_filter)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeToolbarPreview() {
    PicsumGalleryTheme {
        HomeToolbar(homeStatePreview) {}
    }
}
