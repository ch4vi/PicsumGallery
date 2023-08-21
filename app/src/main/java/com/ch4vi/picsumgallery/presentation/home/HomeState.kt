package com.ch4vi.picsumgallery.presentation.home

import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.domain.model.Picture

data class HomeState(
    val list: List<Picture> = emptyList(),
    val imageOrder: ImageOrder = ImageOrder.None(OrderType.Ascending),
    val isLoading: Boolean = true,
    val isSortSectionVisible: Boolean = false,
    val filterAuthor: String? = null,
    val isAuthorFilterExpanded: Boolean = false,
    val authorOptions: List<String> = emptyList()
)
