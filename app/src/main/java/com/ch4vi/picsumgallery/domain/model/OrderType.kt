package com.ch4vi.picsumgallery.domain.model

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
