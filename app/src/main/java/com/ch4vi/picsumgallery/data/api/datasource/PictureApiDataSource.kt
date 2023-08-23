package com.ch4vi.picsumgallery.data.api.datasource

import com.ch4vi.picsumgallery.data.api.datasource.PictureApiMapper.toDomain
import com.ch4vi.picsumgallery.data.api.service.PictureService
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.Picture
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class PictureApiDataSource @Inject constructor(
    private val service: PictureService
) {

    suspend fun fetchPicturePage(page: Int): List<Picture> {
        return try {
            service.fetchPicsumPage(page = page).map { it.toDomain() }
        } catch (t: Throwable) {
            throw onError(t)
        }
    }

    private fun onError(failure: Throwable): Throwable {
        return when (failure) {
            is ConnectException,
            is SocketTimeoutException,
            is UnknownHostException,
            is HttpException -> Failure.NetworkFailure(failure.message)

            is Failure -> failure
            else -> Failure.UnexpectedFailure(failure.message)
        }
    }
}
