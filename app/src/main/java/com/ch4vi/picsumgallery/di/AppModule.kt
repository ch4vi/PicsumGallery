package com.ch4vi.picsumgallery.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.ch4vi.picsumgallery.Constants
import com.ch4vi.picsumgallery.data.api.NetworkConnectivity
import com.ch4vi.picsumgallery.data.api.RetrofitConfiguration
import com.ch4vi.picsumgallery.data.api.datasource.PictureApiDataSource
import com.ch4vi.picsumgallery.data.api.service.PictureService
import com.ch4vi.picsumgallery.data.database.AppDatabase
import com.ch4vi.picsumgallery.data.preferences.datasource.UserPreferencesDatasource
import com.ch4vi.picsumgallery.data.repository.ConnectivityRepositoryImp
import com.ch4vi.picsumgallery.data.repository.PicturesRepositoryImp
import com.ch4vi.picsumgallery.domain.repository.ConnectivityRepository
import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.NAME).build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideRetrofitClient(): Retrofit = RetrofitConfiguration().client()

    @Provides
    fun providePictureService(retrofit: Retrofit): PictureService =
        retrofit.create(PictureService::class.java)

    @Provides
    fun provideNetworkConnectivity(app: Application) = NetworkConnectivity(app)

    @Provides
    fun provideUserPreferencesDatasource(app: Application) =
        UserPreferencesDatasource(
            app.getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE)
        )

    @Provides
    fun providePictureApiDataSource(service: PictureService): PictureApiDataSource {
        return PictureApiDataSource(service)
    }

    @Provides
    fun provideConnectivityRepository(networkConnectivity: NetworkConnectivity): ConnectivityRepository =
        ConnectivityRepositoryImp(networkConnectivity)

    @Provides
    fun providePicturesRepository(
        appDatabase: AppDatabase,
        apiDataSource: PictureApiDataSource,
        preferencesDatasource: UserPreferencesDatasource,
        networkConnectivity: NetworkConnectivity
    ): PicturesRepository =
        PicturesRepositoryImp(
            apiDatasource = apiDataSource,
            databaseDataSource = appDatabase.pictureDao(),
            preferencesDatasource = preferencesDatasource,
            networkConnectivity = networkConnectivity
        )
}
