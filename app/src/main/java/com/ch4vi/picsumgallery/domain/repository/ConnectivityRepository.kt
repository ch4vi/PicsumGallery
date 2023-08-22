package com.ch4vi.picsumgallery.domain.repository

interface ConnectivityRepository {
    fun isOnline(): Boolean
}
