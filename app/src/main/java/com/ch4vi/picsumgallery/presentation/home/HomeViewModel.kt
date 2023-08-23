package com.ch4vi.picsumgallery.presentation.home

import androidx.annotation.StringRes
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch4vi.picsumgallery.CommonConstants.PER_PAGE
import com.ch4vi.picsumgallery.CommonConstants.START_PAGE
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.domain.usecase.CheckConnectivity
import com.ch4vi.picsumgallery.domain.usecase.GetPicturesGallery
import com.ch4vi.picsumgallery.domain.usecase.GetStoredAuthors
import com.ch4vi.picsumgallery.domain.usecase.GetUserState
import com.ch4vi.picsumgallery.domain.usecase.StoreUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.math.max

sealed class HomeEvent {
    object Init : HomeEvent()
    object GetNextPage : HomeEvent()
    object Retry : HomeEvent()
    object ToggleSortingVisibility : HomeEvent()
    data class OnSortingChange(val order: ImageOrder) : HomeEvent()
    object ToggleAuthorMenuVisibility : HomeEvent()
    data class OnAuthorMenuSelected(val author: String) : HomeEvent()
    object OnClearResults : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPicturesGallery: GetPicturesGallery,
    private val storeUserState: StoreUserState,
    private val getUserState: GetUserState,
    private val getStoredAuthors: GetStoredAuthors,
    private val checkConnectivity: CheckConnectivity
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val lastPage
        get() = max(1, (_state.value.list.size / PER_PAGE))

    private var getListJob: Job? = null
    private var getAuthorsJob: Job? = null

    sealed class UiEvent {
        data class ShowSnackbar(@StringRes val message: Int) : UiEvent()
    }

    init {
        dispatch(HomeEvent.Init)
    }

    fun dispatch(event: HomeEvent) {
        updateConnectivityState()

        when (event) {
            HomeEvent.Init -> initialLoad()
            HomeEvent.Retry -> getPictureList(false, lastPage + 1)
            HomeEvent.GetNextPage -> getPictureList(false, lastPage + 1)
            HomeEvent.ToggleSortingVisibility -> toggleSortingVisibility()
            HomeEvent.ToggleAuthorMenuVisibility -> toggleAuthorFilterVisibility()
            is HomeEvent.OnAuthorMenuSelected -> {
                updateAuthor(event.author)
                getPictureList(false, lastPage)
            }

            is HomeEvent.OnSortingChange -> {
                updateImageOrder(event.order)
                getPictureList(false, lastPage)
            }

            HomeEvent.OnClearResults -> onClearResults()
        }
    }

    private fun initialLoad() {
        _state.value = state.value.copy(userState = getUserState())
        getPictureList(false, START_PAGE)
    }

    private fun onClearResults() {
        updateAuthor("")
        updateImageOrder(ImageOrder.None(OrderType.Ascending))
        getPictureList(true, START_PAGE)
    }

    private fun getPictureList(clear: Boolean, page: Int) {
        if (_state.value.isLoading) return

        getListJob?.cancel()
        _state.value = state.value.copy(isLoading = true)

        getListJob = getPicturesGallery(clear, page, state.value.userState)
            .catch {
                onError(it)
            }.onEach { pictureList ->
                _state.value = state.value.copy(isLoading = false)
                _state.value = _state.value.copy(list = pictureList)
                updateAuthorOptions()
            }.launchIn(viewModelScope)
    }

    private fun toggleSortingVisibility() {
        _state.value = state.value.copy(isSortSectionVisible = !state.value.isSortSectionVisible)
    }

    private fun updateImageOrder(imageOrder: ImageOrder) {
        val userState = state.value.userState.copy(imageOrder = imageOrder)
        _state.value = state.value.copy(userState = userState)
        storeUserState(userState)
    }

    private fun toggleAuthorFilterVisibility() {
        _state.value =
            state.value.copy(isAuthorFilterExpanded = !state.value.isAuthorFilterExpanded)
    }

    private fun updateAuthor(author: String) {
        val userState = state.value.userState.copy(authorFilter = author)
        _state.value = state.value.copy(
            userState = userState,
            isAuthorFilterExpanded = false
        )
        storeUserState(userState)
    }

    private fun updateAuthorOptions() {
        getAuthorsJob?.cancel()

        getAuthorsJob = getStoredAuthors()
            .catch {
                onError(it)
            }.onEach { authors ->
                _state.value = state.value.copy(authorOptions = listOf("") + authors)
            }.launchIn(viewModelScope)
    }

    private fun updateConnectivityState() {
        _state.value = state.value.copy(isOfflineVisible = !checkConnectivity())
    }

    private suspend fun onError(error: Throwable) {
        _state.value = state.value.copy(isLoading = false)
        val errorRes = when (error) {
            is Failure.NetworkFailure -> R.string.failure_network
            else -> R.string.failure_generic
        }
        _eventFlow.emit(UiEvent.ShowSnackbar(message = errorRes))
    }
}
