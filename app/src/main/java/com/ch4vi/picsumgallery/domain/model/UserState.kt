package com.ch4vi.picsumgallery.domain.model

data class UserState(
    val authorFilter: String = "",
    val imageOrder: ImageOrder = ImageOrder.None(OrderType.Ascending)
)
