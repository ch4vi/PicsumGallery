package com.ch4vi.picsumgallery.data.preferences.datasource

import android.content.SharedPreferences
import com.ch4vi.picsumgallery.data.preferences.datasource.UserPreferencesMapper.toPreferences
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.domain.model.UserState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class UserPreferencesDatasourceTest {

    @RelaxedMockK
    lateinit var preferences: SharedPreferences

    private lateinit var sut: UserPreferencesDatasource

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
        sut = UserPreferencesDatasource(preferences)
    }

    @Test
    fun `GIVEN user state WHEN store THEN preferences is called`() {
        val userState = UserState("foo", ImageOrder.Height(OrderType.Ascending))
        val expected = userState.imageOrder.toPreferences()

        runBlocking {
            sut.storeUserState(userState)

            verify { preferences.edit().putString("Author", "foo") }
            verify { preferences.edit().putInt("ImageOrder", expected.first) }
            verify { preferences.edit().putInt("OrderType", expected.second) }
        }
    }

    @Test
    fun `GIVEN stored user state WHEN get THEN retrieves model`() {
        coEvery { preferences.getString(any(), any()) } returns "foo"
        coEvery { preferences.getInt(any(), any()) } returns 0

        runBlocking {
            val userState = sut.getUserState()

            assertEquals(userState.authorFilter, "foo")
            assertIs<ImageOrder.Height>(userState.imageOrder)
            assertIs<OrderType.Ascending>(userState.imageOrder.orderType)
        }
    }

    @Test
    fun `GIVEN stored user state WHEN get fails THEN returns initial user state`() {
        coEvery { preferences.getString(any(), any()) } returns "foo"
        coEvery { preferences.getInt(any(), any()) } returns 5

        runBlocking {
            val userState = sut.getUserState()

            assert(userState.authorFilter.isEmpty())
            assertIs<ImageOrder.None>(userState.imageOrder)
            assertIs<OrderType.Ascending>(userState.imageOrder.orderType)
        }
    }
}
