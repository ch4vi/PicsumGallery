package com.ch4vi.picsumgallery.domain.usecase

import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStoredAuthors @Inject constructor(
    private val repository: PicturesRepository
) {
    operator fun invoke() = flow {
        emit(repository.getAuthors())
    }
}
