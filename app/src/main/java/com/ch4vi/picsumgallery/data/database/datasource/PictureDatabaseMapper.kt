package com.ch4vi.picsumgallery.data.database.datasource

import com.ch4vi.picsumgallery.data.database.model.PictureCache
import com.ch4vi.picsumgallery.domain.model.Picture

object PictureDatabaseMapper {

    fun PictureCache.toDomain() = Picture(id, author, width, height, url, downloadUrl)
    fun Picture.toCache() = PictureCache(id, author, width, height, url, downloadUrl)
}
