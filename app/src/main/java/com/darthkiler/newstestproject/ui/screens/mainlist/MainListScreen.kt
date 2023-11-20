package com.darthkiler.newstestproject.ui.screens.mainlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.darthkiler.newstestproject.domain.model.ContentModel
import com.darthkiler.newstestproject.ui.common.ScreenLoading

@Composable
fun MainListScreen(
    viewModel: MainListScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val resultsPagingItems = viewModel.resultsListState.collectAsLazyPagingItems()

    if (uiState.isLoading) {
        ScreenLoading(
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16f.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8f.dp)
            ) {
                items(resultsPagingItems.itemCount) { index ->
                    resultsPagingItems[index]?.let {
                        ContentItem(item = it) {
                            it.id?.let {
                                uiState.onEvent(
                                    MainListScreenViewModel.MainListScreenEvent.OnItemClick(it)
                                )
                            }
                        }
                    }
                }

                resultsPagingItems.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            item { LoadingNextPageItem() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentItem(
    modifier: Modifier = Modifier,
    item: ContentModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16f.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100f.dp)
                    .clip(
                        RoundedCornerShape(16f.dp)
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4f.dp)
            ) {
                item.title?.let {
                    Text(
                        text = item.title,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 22.sp,
                            lineHeight = 24.sp,
                            color = Color(0xff3b2a7a)
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                item.category?.let {
                    Text(
                        text = item.category,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                            color = Color(0xaa3b2a7a)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingNextPageItem(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
