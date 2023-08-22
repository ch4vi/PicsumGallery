package com.ch4vi.picsumgallery.data.repository

import com.ch4vi.picsumgallery.CommonConstants.PER_PAGE
import com.ch4vi.picsumgallery.data.api.NetworkConnectivity
import com.ch4vi.picsumgallery.data.api.datasource.PictureApiDataSource
import com.ch4vi.picsumgallery.data.database.datasource.PictureDao
import com.ch4vi.picsumgallery.data.database.datasource.PictureDatabaseMapper.toCache
import com.ch4vi.picsumgallery.data.database.datasource.PictureDatabaseMapper.toDomain
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import com.ch4vi.picsumgallery.domain.util.Resource
import com.ch4vi.picsumgallery.domain.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PicturesRepositoryImp(
    private val apiDatasource: PictureApiDataSource,
    private val databaseDataSource: PictureDao,
    private val networkConnectivity: NetworkConnectivity
) : PicturesRepository {
    override fun getPictures(clear: Boolean, page: Int): Flow<Resource<List<Picture>>> {
        return networkBoundResource(
            prepareQuery = {
                if (clear) {
                    databaseDataSource.clearAll()
                }
            },
            query = {
                val cachedPages = databaseDataSource.getAllPictures(
                    limit = PER_PAGE,
                    offset = (page - 1) * PER_PAGE
                )
                cachedPages.map { cachedPictures ->
                    cachedPictures?.map { it.toDomain() }
                }
            },
            fetch = {
                apiDatasource.fetchPicturePage(page)
            },
            saveFetchResult = { results ->
                val pageCache = results.map { it.toCache() }
                databaseDataSource.insertPage(pageCache)
            },
            shouldFetch = { cachedPages ->
                networkConnectivity.isOnline() && (cachedPages.isEmpty() || clear)
            }
        )
    }

    override suspend fun getAuthors(): List<String> {
        return databaseDataSource.getAuthors().first() ?: emptyList()
    }
}
