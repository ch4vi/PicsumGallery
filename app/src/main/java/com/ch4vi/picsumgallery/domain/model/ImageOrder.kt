package com.ch4vi.picsumgallery.domain.model

sealed class ImageOrder(val orderType: OrderType) {
    class Width(orderType: OrderType) : ImageOrder(orderType)
    class Height(orderType: OrderType) : ImageOrder(orderType)
    class None(orderType: OrderType) : ImageOrder(orderType)

    fun copy(orderType: OrderType): ImageOrder {
        return when (this) {
            is Width -> Width(orderType)
            is Height -> Height(orderType)
            is None -> None(orderType)
        }
    }
}
