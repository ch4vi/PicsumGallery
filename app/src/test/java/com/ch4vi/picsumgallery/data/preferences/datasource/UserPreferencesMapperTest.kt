package com.ch4vi.picsumgallery.data.preferences.datasource

import com.ch4vi.picsumgallery.data.preferences.datasource.UserPreferencesMapper.toImageOrder
import com.ch4vi.picsumgallery.data.preferences.datasource.UserPreferencesMapper.toPreferences
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class UserPreferencesMapperTest {

    private lateinit var mapper: UserPreferencesMapper

    @BeforeTest
    fun setUp() {
        mapper = UserPreferencesMapper
    }

    @Test
    fun `GIVEN imageOrder domain model WHEN map THEN return filled pair`() {
        val heightAsc = ImageOrder.Height(OrderType.Ascending)
        val heightDesc = ImageOrder.Height(OrderType.Descending)
        val noneAsc = ImageOrder.None(OrderType.Ascending)
        val noneDesc = ImageOrder.None(OrderType.Descending)
        val widthAsc = ImageOrder.Width(OrderType.Ascending)
        val widthDesc = ImageOrder.Width(OrderType.Descending)

        assertEquals(0 to 0, heightAsc.toPreferences())
        assertEquals(0 to 1, heightDesc.toPreferences())
        assertEquals(1 to 0, noneAsc.toPreferences())
        assertEquals(1 to 1, noneDesc.toPreferences())
        assertEquals(2 to 0, widthAsc.toPreferences())
        assertEquals(2 to 1, widthDesc.toPreferences())
    }

    @Test
    fun `GIVEN imageOrder pair WHEN map THEN return filled image order`() {
        val heightAsc = (0 to 0).toImageOrder()
        val heightDesc = (0 to 1).toImageOrder()
        val noneAsc = (1 to 0).toImageOrder()
        val noneDesc = (1 to 1).toImageOrder()
        val widthAsc = (2 to 0).toImageOrder()
        val widthDesc = (2 to 1).toImageOrder()

        assertIs<ImageOrder.Height>(heightAsc)
        assertIs<OrderType.Ascending>(heightAsc.orderType)

        assertIs<ImageOrder.Height>(heightDesc)
        assertIs<OrderType.Descending>(heightDesc.orderType)

        assertIs<ImageOrder.None>(noneAsc)
        assertIs<OrderType.Ascending>(noneAsc.orderType)

        assertIs<ImageOrder.None>(noneDesc)
        assertIs<OrderType.Descending>(noneDesc.orderType)

        assertIs<ImageOrder.Width>(widthAsc)
        assertIs<OrderType.Ascending>(widthAsc.orderType)

        assertIs<ImageOrder.Width>(widthDesc)
        assertIs<OrderType.Descending>(widthDesc.orderType)
    }

    @Test(expected = Failure.MapperFailure::class)
    fun `GIVEN imageOrder pair WHEN map fail THEN throw exception`() {
        (5 to 0).toImageOrder()
    }

    @Test(expected = Failure.MapperFailure::class)
    fun `GIVEN orderType pair WHEN map fail THEN throw exception`() {
        (0 to 5).toImageOrder()
    }
}
