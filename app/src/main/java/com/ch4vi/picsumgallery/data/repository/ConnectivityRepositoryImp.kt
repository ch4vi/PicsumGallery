package com.ch4vi.picsumgallery.data.repository

import com.ch4vi.picsumgallery.data.api.NetworkConnectivity
import com.ch4vi.picsumgallery.domain.repository.ConnectivityRepository

class ConnectivityRepositoryImp(
    private val networkConnectivity: NetworkConnectivity
) : ConnectivityRepository {
    override fun isOnline() = networkConnectivity.isOnline()
}
