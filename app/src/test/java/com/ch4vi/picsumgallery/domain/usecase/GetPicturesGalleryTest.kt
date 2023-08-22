package com.ch4vi.picsumgallery.domain.usecase

import com.ch4vi.picsumgallery.domain.DomainConstants
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.domain.model.UserState
import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetPicturesGalleryTest {

    @RelaxedMockK
    lateinit var repository: PicturesRepository

    private val domainConstants by lazy { DomainConstants }
    private lateinit var sut: GetPicturesGallery

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)

        sut = GetPicturesGallery(repository)
    }

    @Test
    fun `GIVEN useCase WHEN invoke with ImageOrder None THEN returns sorted list`() {
        coEvery {
            repository.getPictures(any(), any(), any())
        } returns flowOf(domainConstants.pictures)

        val expected = listOf(1, 2, 3)
        runBlocking {
            val output =
                sut(true, 1, UserState("", ImageOrder.None(OrderType.Ascending))).first()

            assertEquals(expected, output.map { it.id })
        }
    }

    @Test
    fun `GIVEN useCase WHEN invoke with ImageOrder Width Ascending THEN returns sorted list`() {
        coEvery {
            repository.getPictures(any(), any(), any())
        } returns flowOf(domainConstants.pictures)

        val expected = listOf(3, 2, 1)
        runBlocking {
            val output =
                sut(true, 1, UserState("", ImageOrder.Width(OrderType.Ascending))).first()

            assertEquals(expected, output.map { it.id })
        }
    }

    @Test
    fun `GIVEN useCase WHEN invoke with ImageOrder Height Descending THEN returns sorted list`() {
        coEvery {
            repository.getPictures(any(), any(), any())
        } returns flowOf(domainConstants.pictures)

        val expected = listOf(1, 3, 2)
        runBlocking {
            val output =
                sut(true, 1, UserState("", ImageOrder.Height(OrderType.Descending))).first()

            assertEquals(expected, output.map { it.id })
        }
    }
}
