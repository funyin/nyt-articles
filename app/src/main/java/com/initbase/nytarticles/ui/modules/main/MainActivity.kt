package com.initbase.nytarticles.ui.modules.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.initbase.nytarticles.ui.components.AppEmptyState
import com.initbase.nytarticles.ui.components.AppErrorState
import com.initbase.nytarticles.ui.components.AppLoadingState
import com.initbase.nytarticles.ui.components.AppScaffold
import com.initbase.nytarticles.ui.modules.main.viewModel.MainViewModel
import com.initbase.nytarticles.utils.CallState
import com.initbase.nytarticles.utils.toReadableDate
import com.skydoves.landscapist.glide.GlideImage

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()

    override fun onStart() {
        super.onStart()
        viewModel.getMostViewedArticles()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                ScreenContent()
            }
        }
    }

    @Composable
    @Preview
    private fun ScreenContent() {
        AppScaffold(topBar = {
            TopAppBar {
                Text(
                    "NYT Articles", modifier = Modifier
                        .weight(1f)
                        .align(alignment = Alignment.CenterVertically), textAlign = TextAlign.Center
                )
            }
        }) {
            val callState by viewModel.getArticlesCallState
            when (callState) {
                is CallState.ErrorCallState -> AppErrorState(
                    message = callState.message,
                    onRetry = viewModel::getMostViewedArticles
                )
                is CallState.InitialCallState,
                is CallState.LoadingCallState -> AppLoadingState()
                is CallState.SuccessCallState -> {
                    val data = callState.data?.results!!
                    if (data.isEmpty())
                        AppEmptyState(title = "No Articles Found", subtitle = "Check back later")
                    else
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 32.dp)
                        ) {
                            items(items = data) { article ->
                                Card(modifier = Modifier.padding(horizontal = 24.dp)) {
                                    Column(
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Column(modifier = Modifier
                                                .fillMaxHeight()
                                                .weight(1f)
                                                .padding(end = 8.dp)) {
                                                Text(
                                                    article.title,
                                                    maxLines = 3,
                                                    overflow = TextOverflow.Ellipsis,
                                                    color = Color.Black,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.W700,
                                                    modifier = Modifier.padding(bottom=8.dp)
                                                )
                                                Row(verticalAlignment = Alignment.CenterVertically,modifier=Modifier.fillMaxWidth()) {
                                                    Surface(
                                                        shape = CircleShape,
                                                        modifier = Modifier.size(24.dp),
                                                        color = MaterialTheme.colors.primary,
                                                        contentColor = Color.White
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxSize()
                                                                .padding(2.dp),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(getSectionInitials(article.section), fontSize = 11.sp,maxLines = 1,overflow = TextOverflow.Clip)
                                                        }
                                                    }
                                                    Text(
                                                        article.section,
                                                        modifier = Modifier.padding(start=8.dp),
                                                        fontSize = 14.sp,
                                                        fontWeight = FontWeight.W400,
                                                        maxLines =1
                                                    )
                                                }
                                            }
                                            GlideImage(
                                                imageModel = article.media.firstOrNull { it.type == "image" }?.mediaMetadata?.elementAtOrNull(
                                                    1
                                                )?.url ?: "",
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .size(100.dp)
                                                    .clip(
                                                        RoundedCornerShape(8.dp)
                                                    ),
                                                failure = {
                                                    Surface(modifier = Modifier
                                                        .size(100.dp)
                                                        .background(
                                                            shape = RoundedCornerShape(8.dp),color = Color.Gray
                                                        )) {

                                                    }
                                                }
                                            )
                                        }
                                        Text(
                                            article.abstract,
                                            maxLines = 6,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                                        )
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                article.publishedDate.toReadableDate(),
                                                fontSize = 12.sp,
                                                color = Color(0xff989BA9)
                                            )
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Text(
                                                buildAnnotatedString {
                                                    append("By ")
                                                    withStyle(style = SpanStyle(color = Color.Black)) {
                                                        append(article.byline.replace("By", "").trim())
                                                    }
                                                },
                                                fontSize = 12.sp,
                                                color = Color(0xff989BA9),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                textAlign = TextAlign.End
                                            )
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    private fun getSectionInitials(name: String): String {
        return name.split(" ").joinToString(separator = "") { it.first().toString() }
    }
}