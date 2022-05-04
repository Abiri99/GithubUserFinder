package com.example.githubuserfinder.user_finder.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.githubuserfinder.core.presentation.CustomTextStyle
import com.example.githubuserfinder.core.presentation.Emoji
import com.example.githubuserfinder.core.presentation.component.TouchableScale
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
inline fun <reified T, reified Y> CustomVerticalGridWithPagination(
    initialData: List<T>?,
    crossinline fetchPageCallback: suspend (Int) -> Result<Y>,
    crossinline apiResultToListConverter: (Y) -> List<T>,
    crossinline itemBuilder: @Composable (T) -> Unit,
    crossinline maxCountCalculator: (Y) -> Int,
    modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()

    var items by remember { mutableStateOf<List<T>>(listOf()) }
    var maxCount by remember { mutableStateOf(Int.MAX_VALUE) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var lastPageFetched by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()

    LaunchedEffect(initialData) {
        initialData?.let {
            items = it
        }
    }

    val fetchNextPage: suspend () -> Unit = suspend {
        val result = fetchPageCallback(++lastPageFetched)
        when {
            result.isSuccess -> {
                if (lastPageFetched == 1) {
                    maxCount = maxCountCalculator(result.getOrNull()!!)
                }
                errorMessage = null
                items =
                    listOf(
                        *items.toTypedArray(),
                        *apiResultToListConverter(result.getOrNull()!!).toTypedArray()
                    )
            }
            result.isFailure -> {
                errorMessage =
                    Emoji.womanFacePalming + Emoji.manFacePalming + "\n" + "Failed retrieve the list"
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect {
                if (
                    items.size < maxCount &&
                    (
                        (it / 10 >= lastPageFetched - 1) ||
                            lastPageFetched == 0
                        )
                ) {
                    coroutineScope.launch {
                        fetchNextPage()
                    }
                }
            }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(180.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            items(items.size) { index ->
                itemBuilder(items[index])
            }

            if (items.size < maxCount && errorMessage.isNullOrBlank()) {
                item {
                    LinearProgressIndicator(
                        color = Color.Black,
                        backgroundColor = Color.Transparent,
                        modifier = Modifier.requiredWidth(24.dp),
                    )
                }
            }

            if (
                !errorMessage.isNullOrBlank() ||
                (items.isNotEmpty() && !errorMessage.isNullOrBlank())
            ) {
                item {
                    TouchableScale(onClick = {
                        coroutineScope.launch {
                            fetchNextPage()
                        }
                    }) {
                        Text(
                            text = "$errorMessage, tap to retry!",
                            style = CustomTextStyle.content.copy(
                                color = Color.Red,
                            ),
                        )
                    }
                }
            }
        }

        if (maxCount == 0) {
            Text(
                text = "No result!!",
                modifier = Modifier.align(Alignment.Center),
                style = CustomTextStyle.contentLargeSemiBold,
            )
        }

        if (items.isEmpty() && !errorMessage.isNullOrBlank()) {
            CircularProgressIndicator(
                modifier = Modifier.requiredSize(48.dp),
                color = Color.Black,
                strokeWidth = 4.dp,
            )
        }
    }
}
