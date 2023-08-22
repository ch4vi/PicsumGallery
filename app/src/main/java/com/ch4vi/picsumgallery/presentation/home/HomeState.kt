package com.ch4vi.picsumgallery.presentation.home

import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.model.UserState

data class HomeState(
    val list: List<Picture> = emptyList(),
    val userState: UserState = UserState(),
    val isLoading: Boolean = false,
    val isOfflineVisible: Boolean = false,
    val isSortSectionVisible: Boolean = false,
    val isAuthorFilterExpanded: Boolean = false,
    val authorOptions: List<String> = emptyList()
)
