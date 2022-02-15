package com.initbase.nytarticles.ui.components

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.initbase.nytarticles.ui.theme.NYTArticlesTheme

@Composable
fun AppScaffold(topBar: @Composable () -> Unit = {}, content: @Composable () -> Unit) {
    NYTArticlesTheme {
        Scaffold(topBar = topBar) {
            content()
        }
    }
}