package com.ch4vi.picsumgallery.domain.util

sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Loading<T>(val data: T? = null) : Resource<T>()
    class Error<T>(val throwable: Throwable, val data: T? = null) : Resource<T>()
}
