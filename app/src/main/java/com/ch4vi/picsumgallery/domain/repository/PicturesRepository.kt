package com.ch4vi.picsumgallery.domain.repository

import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

    fun getPictures(clear: Boolean, page: Int): Flow<Resource<List<Picture>>>

    suspend fun getAuthors(): List<String>
}
