package com.ch4vi.picsumgallery.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.presentation.home.components.FilterSection
import com.ch4vi.picsumgallery.presentation.home.components.PictureList
import com.ch4vi.picsumgallery.presentation.home.components.SortSection
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeContainer(
        state = viewModel.state.value,
        onEvent = { event ->
            viewModel.dispatch(event)
        }
    )
}

@Composable
fun HomeContainer(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        snackbarHost = { },
        topBar = {
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            AnimatedVisibility(
                visible = state.isSortSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    imageOrder = state.imageOrder,
                    onOrderChange = { order -> onEvent(HomeEvent.OnSortingChange(order)) }
                )
            }
            FilterSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                selectedOption = state.filterAuthor,
                options = state.authorOptions,
                isExpanded = state.isAuthorFilterExpanded,
                onExpandedChange = { onEvent(HomeEvent.ToggleAuthorMenuVisibility) },
                onOptionSelected = { author -> onEvent(HomeEvent.OnAuthorMenuSelected(author)) },
                onClear = { onEvent(HomeEvent.OnClearResults) }
            )
            PictureList(state = state) {
                onEvent(HomeEvent.GetNextPage)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContainerPreview() {
    PicsumGalleryTheme {
        HomeContainer(state = homeStatePreview, onEvent = {})
    }
}

internal val homeStatePreview = HomeState(
    list = listOf(
        Picture(1, "foo", 1, 1, "", ""),
        Picture(1, "foo", 1, 1, "", "")
    ),
    isLoading = true,
    isSortSectionVisible = false,
    filterAuthor = null
)
