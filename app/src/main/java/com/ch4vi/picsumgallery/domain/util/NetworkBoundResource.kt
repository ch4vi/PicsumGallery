package com.ch4vi.picsumgallery.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

inline fun <ResultType> networkBoundResource(
    crossinline prepareQuery: suspend () -> Unit,
    crossinline query: () -> Flow<ResultType?>,
    crossinline fetch: suspend () -> ResultType,
    crossinline saveFetchResult: suspend (ResultType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    prepareQuery()

    val data = query().first()

    if (data == null || shouldFetch(data)) {
        val networkData = fetch()
        saveFetchResult(networkData)
        emit(query().first())
    } else {
        emit(data)
    }
}
