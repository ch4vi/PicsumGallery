package com.ch4vi.picsumgallery.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.model.UserState
import com.ch4vi.picsumgallery.presentation.home.components.FilterSection
import com.ch4vi.picsumgallery.presentation.home.components.HomeToolbar
import com.ch4vi.picsumgallery.presentation.home.components.PictureList
import com.ch4vi.picsumgallery.presentation.home.components.SortSection
import com.ch4vi.picsumgallery.presentation.util.MultiPreview
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val SORT_SECTION_TEST_TAG = "sort_section_tag"
private const val FILTER_SECTION_TEST_TAG = "filter_section_tag"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeViewModel.UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.resources.getString(event.message),
                            actionLabel = context.resources.getString(R.string.retry)
                        ).apply {
                            when (this) {
                                SnackbarResult.Dismissed -> Unit
                                SnackbarResult.ActionPerformed -> viewModel.dispatch(HomeEvent.Retry)
                            }
                        }
                    }
                }
            }
        }
    }
    HomeContainer(
        state = viewModel.state.value,
        snackbarHostState = snackbarHostState,
        onEvent = { event ->
            viewModel.dispatch(event)
        }
    )
}

@Composable
fun HomeContainer(
    state: HomeState,
    snackbarHostState: SnackbarHostState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            HomeToolbar(state = state, onEvent = onEvent)
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
                        .testTag(SORT_SECTION_TEST_TAG)
                        .padding(horizontal = 16.dp),
                    imageOrder = state.userState.imageOrder,
                    onOrderChange = { order -> onEvent(HomeEvent.OnSortingChange(order)) }
                )
            }
            FilterSection(
                modifier = Modifier
                    .testTag(FILTER_SECTION_TEST_TAG)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                selectedOption = state.userState.authorFilter,
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

@MultiPreview
@Composable
fun HomeContainerPreview() {
    PicsumGalleryTheme {
        HomeContainer(
            state = homeStatePreview,
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}

internal val homeStatePreview = HomeState(
    list = listOf(
        Picture(1, "foo", 1, 1, "", ""),
        Picture(1, "foo", 1, 1, "", "")
    ),
    isLoading = true,
    isSortSectionVisible = false,
    userState = UserState()
)
