package com.initbase.nytarticles.ui.modules.main.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.initbase.nytarticles.data.api.ApiHelper
import com.initbase.nytarticles.data.model.NYTResponse
import com.initbase.nytarticles.data.repository.MainRepository
import com.initbase.nytarticles.utils.CallState
import com.initbase.nytarticles.utils.CallState.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = MainRepository()
    val getArticlesCallState = mutableStateOf<CallState<NYTResponse>>(InitialCallState())



    fun getMostViewedArticles() {
        viewModelScope.launch {
            getArticlesCallState.value = LoadingCallState()
            try {
                val response = repository.getMostViewedArticles()
                getArticlesCallState.value = SuccessCallState(response.body(), response.message())
            } catch (e: Exception) {
                getArticlesCallState.value = ErrorCallState(data = null, message = "Unable to get articles")
            }
        }
    }
}