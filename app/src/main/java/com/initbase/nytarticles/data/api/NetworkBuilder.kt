package com.initbase.nytarticles.data.api

import com.initbase.nytarticles.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkBuilder {

    private const val BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/"

    private fun getRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder().addInterceptor (
            Interceptor { chain ->
                val original = chain.request()
                val url = original.url
                val newUrl = url.newBuilder().addQueryParameter("api-key",BuildConfig.apiKey).build()
                chain.proceed(original.newBuilder().url(newUrl).build())
            }).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}