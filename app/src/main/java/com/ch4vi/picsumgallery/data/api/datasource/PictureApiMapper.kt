package com.ch4vi.picsumgallery.data.api.datasource

import com.ch4vi.picsumgallery.data.api.model.PictureDTO
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.Picture

object PictureApiMapper {

    @Throws(Failure.MapperFailure::class)
    fun PictureDTO.toDomain(): Picture {
        return Picture(
            id = id ?: throw Failure.MapperFailure,
            author = author ?: throw Failure.MapperFailure,
            width = width ?: throw Failure.MapperFailure,
            height = height ?: throw Failure.MapperFailure,
            url = url ?: throw Failure.MapperFailure,
            downloadUrl = downloadUrl ?: throw Failure.MapperFailure
        )
    }
}
