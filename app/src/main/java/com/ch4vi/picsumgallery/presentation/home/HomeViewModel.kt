package com.ch4vi.picsumgallery.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class HomeEvent {
    object Init : HomeEvent()
    object GetNextPage : HomeEvent()
    object ToggleSortingVisibility : HomeEvent()
    data class OnSortingChange(val order: ImageOrder) : HomeEvent()
    object ToggleAuthorMenuVisibility : HomeEvent()
    data class OnAuthorMenuSelected(val author: String) : HomeEvent()
    object OnClearResults : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        _state.value = HomeState(
            list = listOf(
                Picture(1, "foo", 1, 1, "", ""),
                Picture(1, "foo", 1, 1, "", "")
            ),
            isLoading = false,
            isSortSectionVisible = false,
            filterAuthor = null,
            isAuthorFilterExpanded = false,
            authorOptions = listOf("name1", "name2", "name3", "name4", "name5", "name6")
        )
    }

    fun dispatch(event: HomeEvent) {
        when (event) {
            HomeEvent.Init -> Unit
            HomeEvent.GetNextPage -> Unit
            HomeEvent.ToggleSortingVisibility -> toggleSortingVisibility()
            is HomeEvent.OnAuthorMenuSelected -> Unit
            is HomeEvent.OnSortingChange -> Unit
            HomeEvent.ToggleAuthorMenuVisibility -> toggleAuthorFilterVisibility()
            HomeEvent.OnClearResults -> Unit
        }
    }

    private fun toggleSortingVisibility() {
        _state.value = state.value.copy(isSortSectionVisible = !state.value.isSortSectionVisible)
    }

    private fun toggleAuthorFilterVisibility() {
        _state.value =
            state.value.copy(isAuthorFilterExpanded = !state.value.isAuthorFilterExpanded)
    }
}
