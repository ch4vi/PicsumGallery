package com.ch4vi.picsumgallery.domain.usecase

import com.ch4vi.picsumgallery.domain.repository.ConnectivityRepository
import javax.inject.Inject

class CheckConnectivity @Inject constructor(
    private val repository: ConnectivityRepository
) {
    operator fun invoke() = repository.isOnline()
}
