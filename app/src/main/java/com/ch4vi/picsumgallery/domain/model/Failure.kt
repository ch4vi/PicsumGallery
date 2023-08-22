package com.ch4vi.picsumgallery.domain.model

sealed class Failure(msg: String? = null) : Throwable(msg) {
    object MapperFailure : Failure(null)
    class NetworkFailure(msg: String? = null) : Failure(msg)
    class UnexpectedFailure(msg: String? = null) : Failure(msg)
}
