package com.ch4vi.picsumgallery.data.api.datasource

import com.ch4vi.picsumgallery.data.api.service.PictureService
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.Picture
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import java.net.ConnectException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs


class PictureApiDataSourceTest {

    @RelaxedMockK
    lateinit var service: PictureService

    private val apiConstants by lazy { ApiConstants }
    private lateinit var sut: PictureApiDataSource

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
        sut = PictureApiDataSource(service)
    }

    @Test
    fun `GIVEN success response WHEN fetch THEN returns Page`() {
        coEvery { service.fetchPicsumPage(any(), any()) } returns listOf(
            apiConstants.pictureDTO,
            apiConstants.otherPictureDTO
        )

        runBlocking {
            val output = sut.fetchPicturePage(1).first()

            assertIs<Picture>(output)
        }
    }

    @Test(expected = Failure.NetworkFailure::class)
    fun `GIVEN network error WHEN fetch THEN throws NetworkFailure`() {
        coEvery { service.fetchPicsumPage(any(), any()) } throws ConnectException()

        runBlocking {
            sut.fetchPicturePage(0)
        }
    }
}
