package com.ch4vi.picsumgallery.domain.repository

import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.model.UserState
import kotlinx.coroutines.flow.Flow

interface PicturesRepository {

    fun getPictures(clear: Boolean, page: Int, author: String?): Flow<List<Picture>?>

    suspend fun getAuthors(): List<String>

    fun storeUserState(userState: UserState)

    fun getUserState(): UserState
}
