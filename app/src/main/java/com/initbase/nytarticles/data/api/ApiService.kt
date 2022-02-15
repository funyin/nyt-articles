package com.initbase.nytarticles.data.api

import com.initbase.nytarticles.data.model.NYTResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("viewed/7.json")
    suspend fun getMostViewed():Response<NYTResponse>
}