package com.ch4vi.picsumgallery.data.api.datasource

import com.ch4vi.picsumgallery.data.api.datasource.PictureApiMapper.toDomain
import com.ch4vi.picsumgallery.data.api.model.PictureDTO
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.Picture
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs

class PictureApiMapperTest {

    private lateinit var mapper: PictureApiMapper
    private var moshi: Moshi? = null
    private var pictureListType: Type = Types.newParameterizedType(
        List::class.java,
        PictureDTO::class.java
    )
    private var pageAdapter: JsonAdapter<List<PictureDTO>>? = null
    private var dtoAdapter: JsonAdapter<PictureDTO>? = null
    private val apiConstants by lazy { ApiConstants }

    @BeforeTest
    fun setUp() {
        moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        pageAdapter = moshi?.adapter(pictureListType)
        dtoAdapter = moshi?.adapter(PictureDTO::class.java)
        mapper = PictureApiMapper
    }

    @AfterTest
    fun tearDown() {
        moshi = null
        pageAdapter = null
        dtoAdapter = null
    }

    @Test
    fun `GIVEN page dto WHEN map THEN domain model is filled`() {
        val dto = pageAdapter?.fromJson(apiConstants.picturePageSuccessResponse)
        val output = dto?.map { it.toDomain() }

        assertIs<PictureDTO>(dto?.first())
        assertIs<Picture>(output?.first())
    }

    @Test(expected = Failure.MapperFailure::class)
    fun `GIVEN error page dto WHEN map THEN throw MapperFailure`() {
        val dto = pageAdapter?.fromJson(apiConstants.picturePageErrorResponse)

        dto?.map { it.toDomain() }
    }
}
