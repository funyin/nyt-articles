package com.initbase.nytarticles.data.api

object ApiHelper {
    val service = NetworkBuilder.apiService
    suspend fun getMostViewedArticles()= service.getMostViewed()
}