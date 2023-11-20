package com.darthkiler.newstestproject.ui.screens.mainlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.darthkiler.newstestproject.domain.model.ContentModel
import kotlin.random.Random

@Composable
fun MainListScreen(
    viewModel: MainListScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val resultsPagingItems = viewModel.resultsListState.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                all = 16.dp
            )
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
        }
    }
}


@Composable
private fun ContentItem(
    modifier: Modifier = Modifier,
    item: ContentModel,
    onClick: () -> Unit
) {
    val backgroundColor = remember {
        Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))
    }
    Column(
        modifier = modifier
            .background(
                backgroundColor
            )
            .clickable(
                onClick = onClick
            )
    ) {
        item.id?.let {
            Text(text = item.id)
        }
    }
}