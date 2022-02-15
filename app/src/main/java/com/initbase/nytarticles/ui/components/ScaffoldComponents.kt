package com.initbase.nytarticles.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.initbase.nytarticles.ui.theme.NYTArticlesTheme

@Composable
fun AppScaffold(topBar: @Composable () -> Unit = {},backgroundColor: Color = MaterialTheme.colors.background, content: @Composable () -> Unit) {
    NYTArticlesTheme {
        Scaffold(topBar = topBar,backgroundColor = backgroundColor) {
            content()
        }
    }
}

@Composable
fun AppTopBar(title: String, backIcon: (@Composable () -> Unit)? = null) {
    TopAppBar(
        title = {
            Text(
                title, textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth(),maxLines = 1,overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = backIcon,
    )
}