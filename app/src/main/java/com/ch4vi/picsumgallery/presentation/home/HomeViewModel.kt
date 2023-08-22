package com.ch4vi.picsumgallery.presentation.home

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch4vi.picsumgallery.CommonConstants.START_PAGE
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import com.ch4vi.picsumgallery.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
class HomeViewModel @Inject constructor(
    private val repo: PicturesRepository
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var lastPage = START_PAGE
    private val pictureList = mutableListOf<Picture>()
    private var getListJob: Job? = null

    sealed class UiEvent {
        data class ShowSnackbar(@StringRes val message: Int) : UiEvent()
    }

    init {
        getPictureList(false, START_PAGE)
    }

    fun dispatch(event: HomeEvent) {
        when (event) {
            HomeEvent.Init -> initialLoad()
            HomeEvent.GetNextPage -> getPictureList(false, lastPage + 1)
            HomeEvent.ToggleSortingVisibility -> toggleSortingVisibility()
            is HomeEvent.OnAuthorMenuSelected -> Unit
            is HomeEvent.OnSortingChange -> Unit
            HomeEvent.ToggleAuthorMenuVisibility -> toggleAuthorFilterVisibility()
            HomeEvent.OnClearResults -> onClearResults()
        }
    }

    private fun initialLoad() {
        /*
            Load preferences ( last filters, last sorting, last page)
            get db records
            apply filters
         */
    }

    private fun onClearResults() {
        /*
        Remove preferences
        remove db
        clear filters
        clear sorting
        fetch first page
         */
        _state.value = state.value.copy(isLoading = false, list = emptyList())
        getPictureList(true, START_PAGE)
    }

    private fun getPictureList(clear: Boolean, page: Int) {
        if (_state.value.isLoading) return

        getListJob?.cancel()
        _state.value = state.value.copy(isLoading = true)

        getListJob = repo.getPictures(clear, page)
            .catch {
                _state.value = state.value.copy(isLoading = false)
                _eventFlow.emit(UiEvent.ShowSnackbar(message = onError(it)))
            }.onEach { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = onError(resource.throwable)))
                    }

                    is Resource.Loading -> {
                        resource.data?.let {
                            _state.value = state.value.copy(isLoading = false)
                            pictureList.addAll(resource.data)
                            lastPage = page
                            _state.value = _state.value.copy(list = pictureList)
                        }
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false)
                        pictureList.addAll(resource.data)
                        lastPage = page
                        _state.value = _state.value.copy(list = pictureList)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun toggleSortingVisibility() {
        _state.value = state.value.copy(isSortSectionVisible = !state.value.isSortSectionVisible)
    }

    private fun toggleAuthorFilterVisibility() {
        _state.value =
            state.value.copy(isAuthorFilterExpanded = !state.value.isAuthorFilterExpanded)
    }

    private fun onError(error: Throwable): Int {
        return when (error) {
            is Failure.NetworkFailure -> R.string.failure_network
            else -> R.string.failure_generic
        }
    }
}
