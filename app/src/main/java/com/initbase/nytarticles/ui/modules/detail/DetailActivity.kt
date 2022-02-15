package com.initbase.nytarticles.ui.modules.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.initbase.nytarticles.data.model.Article
import com.initbase.nytarticles.ui.components.AppScaffold
import com.initbase.nytarticles.ui.components.AppTopBar
import com.initbase.nytarticles.utils.toReadableDate
import com.skydoves.landscapist.glide.GlideImage

class DetailActivity : ComponentActivity() {
    companion object {
        val ITEM = "item"
    }

    val article by lazy { intent.getParcelableExtra<Article>(ITEM)!! }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenContent()
        }
    }

    @Composable
    private fun ScreenContent() {
        AppScaffold(topBar = {
            AppTopBar(title = article.title, backIcon = {
                IconButton(onClick = { finish() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
        }, backgroundColor = Color.White) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                GlideImage(
                    imageModel = article.media.firstOrNull { it.type == "image" }?.mediaMetadata?.elementAtOrNull(
                        1
                    )?.url ?: "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .clip(
                            RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        ),
                    failure = {
                        Surface(
                            modifier = Modifier
                                .size(100.dp)
                                .background(
                                    shape = RoundedCornerShape(8.dp), color = Color.Gray
                                )
                        ) {

                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        article.title,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
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
                            textAlign = TextAlign.End
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    )
                    Text(
                        article.abstract,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp), horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            val browserIntent =
                                Intent(Intent.ACTION_VIEW, android.net.Uri.parse("http://www.google.com"));
                            startActivity(browserIntent);
                        }) {
                            Text("Read More")
                        }
                    }
                }
            }
        }
    }
}
