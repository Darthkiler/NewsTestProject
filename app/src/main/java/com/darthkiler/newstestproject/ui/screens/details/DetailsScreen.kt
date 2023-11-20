package com.darthkiler.newstestproject.ui.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.darthkiler.newstestproject.domain.model.ContentModel
import com.darthkiler.newstestproject.ui.common.ScreenLoading

@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                ScreenLoading(
                    modifier = Modifier.fillMaxSize()
                )
            }

            uiState.isError -> {
                DownloadContentError(
                    modifier = Modifier.fillMaxSize()
                ) {
                    uiState.onEvent(
                        DetailsScreenViewModel.DetailsListScreenEvent.OnRetryClick
                    )
                }
            }

            else -> {
                uiState.contentModel?.let {
                    ContentDetails(contentModel = it) {
                        uiState.onEvent(DetailsScreenViewModel.DetailsListScreenEvent.OnLinkClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun DownloadContentError(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Download error, please retry")

        Button(onClick = {
            onClick.invoke()
        }) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun ContentDetails(
    contentModel: ContentModel,
    onLinkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                16f.dp
            ),
        verticalArrangement = Arrangement.spacedBy(8f.dp)
    ) {
        contentModel.imageUrl?.let {
            AsyncImage(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8f.dp)
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(contentModel.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        contentModel.title?.let {
            Text(
                text = it,
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 24.sp,
                    color = Color(0xff3b2a7a)
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        contentModel.category?.let {
            Text(
                text = "Category: $it",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    color = Color(0xaa3b2a7a)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        contentModel.date?.let {
            Text(
                text = "Posted: $it",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    color = Color(0xaa3b2a7a)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        contentModel.pillar?.let {
            Text(
                text = "Pillar: $it",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    color = Color(0xaa3b2a7a)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        contentModel.link?.let { link ->
            Text(
                modifier = Modifier.clickable(onClick = {
                    onLinkClick.invoke()
                }),
                text = remember {
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline,
                                color = Color.Blue
                            )
                        ) {
                            append(link)
                        }
                    }
                },
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 18.sp,
                    color = Color(0xaa3b2a7a)
                )
            )
        }
    }
}
