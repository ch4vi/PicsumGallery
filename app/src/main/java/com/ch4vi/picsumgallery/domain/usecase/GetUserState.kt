package com.ch4vi.picsumgallery.domain.usecase

import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import javax.inject.Inject

class GetUserState @Inject constructor(
    private val repository: PicturesRepository
) {
    operator fun invoke() = repository.getUserState()
}
