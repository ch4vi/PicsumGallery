package com.ch4vi.picsumgallery.data.api

import com.ch4vi.picsumgallery.CommonConstants
import com.ch4vi.picsumgallery.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

class RetrofitConfiguration {
    companion object {
        private const val TIMEOUT: Long = 100
    }

    fun client() = initRetrofit(initMoshi(), initClient())

    private fun initMoshi() = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private fun initClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (Constants.NETWORK_LOGGING_ENABLED) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val headers = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("accept", "application/json")
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(headers)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    private fun initRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(CommonConstants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
}
