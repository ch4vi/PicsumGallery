package com.ch4vi.picsumgallery.data.api.service

import com.ch4vi.picsumgallery.CommonConstants.PER_PAGE
import com.ch4vi.picsumgallery.CommonConstants.START_PAGE
import com.ch4vi.picsumgallery.data.api.model.PictureDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureService {

    companion object {
        private const val LIST = "list"
    }

    @GET(LIST)
    suspend fun fetchPicsumPage(
        @Query("page") page: Int = START_PAGE,
        @Query("limit") limit: Int = PER_PAGE
    ): List<PictureDTO>
}
