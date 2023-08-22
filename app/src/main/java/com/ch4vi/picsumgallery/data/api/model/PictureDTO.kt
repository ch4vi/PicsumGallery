package com.ch4vi.picsumgallery.data.api.model

import com.squareup.moshi.Json

data class PictureDTO(
    val id: Int? = null,
    val author: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null,
    @Json(name = "download_url") val downloadUrl: String? = null
)
