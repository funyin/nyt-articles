package com.initbase.nytarticles.data.repository

import com.initbase.nytarticles.data.api.ApiHelper

class MainRepository {
    private val apiHelper = ApiHelper
    suspend fun getMostViewedArticles() = apiHelper.getMostViewedArticles()
}