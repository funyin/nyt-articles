package com.initbase.nytarticles.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AppErrorState(message: String?, onRetry: ()->Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
        Text(text=message!!,style = MaterialTheme.typography.h6,textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(6.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
@Composable
fun AppEmptyState(title: String, subtitle: String) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
        Text(text=title,style = MaterialTheme.typography.h6,textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = subtitle,style = MaterialTheme.typography.subtitle1,textAlign = TextAlign.Center)
    }
}
@Composable
fun AppLoadingState(){
    Box(modifier = Modifier.fillMaxSize().padding(24.dp),contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier=Modifier.size(30.dp),strokeWidth = 2.dp)
    }
}