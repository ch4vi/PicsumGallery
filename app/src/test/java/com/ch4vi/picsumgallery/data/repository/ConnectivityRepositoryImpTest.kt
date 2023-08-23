package com.ch4vi.picsumgallery.data.repository

import com.ch4vi.picsumgallery.data.api.NetworkConnectivity
import com.ch4vi.picsumgallery.domain.repository.ConnectivityRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class ConnectivityRepositoryImpTest {

    @RelaxedMockK
    lateinit var networkConnectivity: NetworkConnectivity

    private lateinit var sut: ConnectivityRepository

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
        sut = ConnectivityRepositoryImp(networkConnectivity)
    }

    @Test
    fun `GIVEN repo WHEN check online THEN call networkConnectivity`() {
        coEvery { networkConnectivity.isOnline() } returns true

        runBlocking {
            assertTrue { sut.isOnline() }
        }
    }
}
