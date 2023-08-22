package com.ch4vi.picsumgallery.data.preferences.datasource

import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType

object UserPreferencesMapper {

    fun ImageOrder.toPreferences(): Pair<Int, Int> {
        val prefOrderType = this.orderType.toPreferences()
        return when (this) {
            is ImageOrder.Height -> 0
            is ImageOrder.None -> 1
            is ImageOrder.Width -> 2
        } to prefOrderType
    }

    private fun OrderType.toPreferences(): Int {
        return when (this) {
            OrderType.Ascending -> 0
            OrderType.Descending -> 1
        }
    }

    @Throws(Failure.MapperFailure::class)
    fun Pair<Int, Int>.toImageOrder(): ImageOrder {
        val orderType = this.second.toOrderType()
        return when (this.first) {
            0 -> ImageOrder.Height(orderType)
            1 -> ImageOrder.None(orderType)
            2 -> ImageOrder.Width(orderType)
            else -> throw Failure.MapperFailure
        }
    }

    @Throws(Failure.MapperFailure::class)
    private fun Int.toOrderType(): OrderType {
        return when (this) {
            0 -> OrderType.Ascending
            1 -> OrderType.Descending
            else -> throw Failure.MapperFailure
        }
    }
}
