package com.ch4vi.picsumgallery.domain.usecase

import com.ch4vi.picsumgallery.domain.model.UserState
import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import javax.inject.Inject

class StoreUserState @Inject constructor(
    private val repository: PicturesRepository
) {
    operator fun invoke(userState: UserState) =
        repository.storeUserState(userState)
}
