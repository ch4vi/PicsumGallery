package com.ch4vi.picsumgallery.presentation.home


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ch4vi.picsumgallery.domain.DomainConstants
import com.ch4vi.picsumgallery.domain.usecase.CheckConnectivity
import com.ch4vi.picsumgallery.domain.usecase.GetPicturesGallery
import com.ch4vi.picsumgallery.domain.usecase.GetStoredAuthors
import com.ch4vi.picsumgallery.domain.usecase.GetUserState
import com.ch4vi.picsumgallery.domain.usecase.StoreUserState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private val domainConstants by lazy { DomainConstants }

    private var getPicturesGallery: GetPicturesGallery = mockk(relaxed = true) {
        coEvery { this@mockk(any(), any(), any()) } returns flowOf(domainConstants.pictures)
    }

    @RelaxedMockK
    lateinit var storeUserState: StoreUserState

    private var getUserState: GetUserState = mockk(relaxed = true) {
        coEvery { this@mockk() } returns domainConstants.userState
    }

    private var getStoredAuthors: GetStoredAuthors = mockk(relaxed = true) {
        coEvery { this@mockk() } returns flowOf(domainConstants.authors)
    }

    private var checkConnectivity: CheckConnectivity = mockk(relaxed = true) {
        coEvery { this@mockk() } returns true
    }

    private lateinit var sut: HomeViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this)

        sut = HomeViewModel(
            getPicturesGallery,
            storeUserState,
            getUserState,
            getStoredAuthors,
            checkConnectivity
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN viewModel WHEN Init THEN fetch first page`() {
        val expected = HomeState(
            list = domainConstants.pictures,
            userState = domainConstants.userState,
            isLoading = false,
            isOfflineVisible = false,
            isSortSectionVisible = false,
            isAuthorFilterExpanded = false,
            authorOptions = listOf("") + domainConstants.authors,
        )

        runBlocking {
            sut.dispatch(HomeEvent.Init)

            coVerify {
                getPicturesGallery(false, 1, domainConstants.userState)
                getStoredAuthors()
            }

            assertEquals(expected, sut.state.value)
        }
    }

    @Test
    fun `GIVEN viewModel WHEN Retry THEN fetch same page`() {
        runBlocking {
            sut.dispatch(HomeEvent.Retry)

            coVerify {
                getPicturesGallery(false, 1, domainConstants.userState)
                getStoredAuthors()
            }
        }
    }

    @Test
    fun `GIVEN viewModel WHEN GetNextPage THEN fetch same page`() {

        runBlocking {
            sut.dispatch(HomeEvent.GetNextPage)

            coVerify {
                getPicturesGallery(false, 1, domainConstants.userState)
                getStoredAuthors()
            }
        }
    }

    @Test
    fun `GIVEN viewModel WHEN ToggleSortingVisibility THEN fetch same page`() {
        val expected = HomeState(
            list = domainConstants.pictures,
            userState = domainConstants.userState,
            isLoading = false,
            isOfflineVisible = false,
            isSortSectionVisible = true,
            isAuthorFilterExpanded = false,
            authorOptions = listOf("") + domainConstants.authors,
        )

        runBlocking {
            sut.dispatch(HomeEvent.ToggleSortingVisibility)

            assertEquals(expected, sut.state.value)
        }
    }

    @Test
    fun `GIVEN viewModel WHEN ToggleAuthorMenuVisibility THEN fetch same page`() {
        val expected = HomeState(
            list = domainConstants.pictures,
            userState = domainConstants.userState,
            isLoading = false,
            isOfflineVisible = false,
            isSortSectionVisible = true,
            isAuthorFilterExpanded = false,
            authorOptions = listOf("") + domainConstants.authors,
        )

        runBlocking {
            sut.dispatch(HomeEvent.ToggleSortingVisibility)

            assertEquals(expected, sut.state.value)
        }
    }

    @Test
    fun `GIVEN viewModel WHEN OnAuthorMenuSelected THEN fetch same page`() {
        val expected = HomeState(
            list = domainConstants.pictures,
            userState = domainConstants.userState.copy("foo"),
            isLoading = false,
            isOfflineVisible = false,
            isSortSectionVisible = false,
            isAuthorFilterExpanded = false,
            authorOptions = listOf("") + domainConstants.authors,
        )

        runBlocking {
            sut.dispatch(HomeEvent.OnAuthorMenuSelected("foo"))

            assertEquals(expected, sut.state.value)

            coVerify {
                getPicturesGallery(false, 1, domainConstants.userState)
                getStoredAuthors()
                storeUserState(expected.userState)
            }
        }
    }

    @Test
    fun `GIVEN viewModel WHEN OnSortingChange THEN fetch same page`() {
        val expected = HomeState(
            list = domainConstants.pictures,
            userState = domainConstants.otherUserState,
            isLoading = false,
            isOfflineVisible = false,
            isSortSectionVisible = false,
            isAuthorFilterExpanded = false,
            authorOptions = listOf("") + domainConstants.authors,
        )
        coEvery { getUserState() } returns domainConstants.otherUserState

        runBlocking {
            sut.dispatch(HomeEvent.OnSortingChange(domainConstants.otherUserState.imageOrder))

            assertEquals(expected, sut.state.value)

            coVerify {
                getPicturesGallery(false, 1, domainConstants.otherUserState)
                getStoredAuthors()
                storeUserState(expected.userState)
            }
        }
    }

    @Test
    fun `GIVEN viewModel WHEN OnClearResults THEN fetch same page`() {

        runBlocking {
            sut.dispatch(HomeEvent.OnClearResults)

            coVerify {
                getPicturesGallery(true, 1, any())
                storeUserState(domainConstants.userState)
            }
        }
    }
}
