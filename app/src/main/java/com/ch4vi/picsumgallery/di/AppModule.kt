package com.ch4vi.picsumgallery.di

import com.ch4vi.picsumgallery.data.api.RetrofitConfiguration
import com.ch4vi.picsumgallery.data.api.datasource.PictureApiDataSource
import com.ch4vi.picsumgallery.data.api.service.PictureService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideRetrofitClient(): Retrofit = RetrofitConfiguration().client()

    @Provides
    fun providePictureService(retrofit: Retrofit): PictureService =
        retrofit.create(PictureService::class.java)

    @Provides
    fun providePictureApiDataSource(service: PictureService): PictureApiDataSource {
        return PictureApiDataSource(service)
    }
}
