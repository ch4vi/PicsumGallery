package com.ch4vi.picsumgallery.domain.model

import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType

data class UserState(
    val authorFilter: String = "",
    val imageOrder: ImageOrder = ImageOrder.None(OrderType.Ascending),
)
