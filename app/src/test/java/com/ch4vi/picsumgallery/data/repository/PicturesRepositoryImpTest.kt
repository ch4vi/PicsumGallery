package com.ch4vi.picsumgallery.data.repository

import com.ch4vi.picsumgallery.data.api.NetworkConnectivity
import com.ch4vi.picsumgallery.data.api.datasource.PictureApiDataSource
import com.ch4vi.picsumgallery.data.database.datasource.PictureDao
import com.ch4vi.picsumgallery.data.preferences.datasource.UserPreferencesDatasource
import com.ch4vi.picsumgallery.domain.model.UserState
import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class PicturesRepositoryImpTest {

    private var apiDatasource: PictureApiDataSource = mockk(relaxed = true) {
        coEvery { fetchPicturePage(any()) } returns emptyList()
    }

    private var databaseDataSource: PictureDao = mockk(relaxed = true) {
        coEvery { getAuthors() } returns flowOf(emptyList())
        coEvery { getAllPictures() } returns flowOf(emptyList())
        coEvery { getPicturesByAuthor(any()) } returns flowOf(emptyList())
        coEvery { insertPage(any()) } just Runs
        coEvery { clearAll() } just Runs
    }

    @RelaxedMockK
    lateinit var preferencesDatasource: UserPreferencesDatasource

    @RelaxedMockK
    private var networkConnectivity: NetworkConnectivity = mockk(relaxed = true) {
        coEvery { isOnline() } returns true
    }

    private lateinit var sut: PicturesRepository

    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic("androidx.room.RoomDatabaseKt")

        sut = PicturesRepositoryImp(
            apiDatasource,
            databaseDataSource,
            preferencesDatasource,
            networkConnectivity
        )
    }

    @Test
    fun `GIVEN repo WHEN getAuthors THEN call datasource`() {
        runBlocking {
            sut.getAuthors()

            verify(exactly = 1) { databaseDataSource.getAuthors() }
        }
    }

    @Test
    fun `GIVEN repo WHEN storeUserState THEN call datasource`() {
        val expected = UserState()

        runBlocking {
            sut.storeUserState(expected)

            verify(exactly = 1) { preferencesDatasource.storeUserState(expected) }
        }
    }

    @Test
    fun `GIVEN repo WHEN getUserState THEN call datasource`() {
        runBlocking {
            sut.getUserState()

            verify(exactly = 1) { preferencesDatasource.getUserState() }
        }
    }

    @Test
    fun `GIVEN repo WHEN getAllPictures with force clear THEN call the correct datasource`() {
        runBlocking {
            sut.getPictures(true, 1, null).first()

            coVerifyOrder {
                databaseDataSource.clearAll()
                databaseDataSource.getAllPictures()
                networkConnectivity.isOnline()
                apiDatasource.fetchPicturePage(1)
                databaseDataSource.insertPage(any())
                databaseDataSource.getAllPictures()
            }
        }
    }

    @Test
    fun `GIVEN repo WHEN getAuthorPictures without clear THEN call the correct datasource`() {
        runBlocking {
            sut.getPictures(false, 1, "foo").first()

            coVerifyOrder {
                databaseDataSource.getPicturesByAuthor("foo")
                networkConnectivity.isOnline()
            }

            coVerify(exactly = 0) { databaseDataSource.clearAll() }
            coVerify(exactly = 0) { apiDatasource.fetchPicturePage(1) }
        }
    }

    @Test
    fun `GIVEN repo WHEN getPictures without network THEN call the correct datasource`() {
        coEvery { networkConnectivity.isOnline() } returns false

        runBlocking {
            sut.getPictures(false, 1, null).first()

            coVerifyOrder {
                databaseDataSource.getAllPictures()
                networkConnectivity.isOnline()
            }

            coVerify(exactly = 0) { databaseDataSource.clearAll() }
            coVerify(exactly = 0) { apiDatasource.fetchPicturePage(1) }
        }
    }
}
