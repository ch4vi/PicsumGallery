package com.ch4vi.picsumgallery.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <ResultType> networkBoundResource(
    crossinline query: () -> Flow<ResultType?>,
    crossinline prepareQuery: suspend () -> Unit,
    crossinline fetch: suspend () -> ResultType,
    crossinline saveFetchResult: suspend (ResultType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    prepareQuery.invoke()
    emit(Resource.Loading(null))

    val data = query().first()

    if (data == null || shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            val networkData = fetch()
            saveFetchResult(networkData)
            emit(Resource.Success(networkData))
        } catch (t: Throwable) {
            emit(Resource.Error(t, data))
        }
    } else {
        emit(Resource.Success(data))
    }
}
