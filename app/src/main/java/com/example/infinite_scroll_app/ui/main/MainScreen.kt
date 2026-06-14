package com.example.infinite_scroll_app.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infinite_scroll_app.ui.theme.InfinitescrollappTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()
    val uiListState = viewModel.uiList.collectAsState()
    val list = uiListState.value
    val lazyListState = rememberLazyListState()

    /**
     * 下側にbuffer(=20件)よりも下回った場合にページング処理を行う
     */
    val isRequiredMoreLoadAfter = remember {
        derivedStateOf {
            val buffer = 20
            val lastVisibleIndex = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            uiListState.value.size - lastVisibleIndex <= buffer
        }
    }

    /**
     * 上側にbuffer(=20件)よりも近づいた場合に上向きページング処理を行う
     * NOTE: 上端はインデックス0で固定なので、先頭可視インデックスそのものが先頭からの距離になる
     */
    val isRequiredMoreLoadBefore = remember {
        derivedStateOf {
            val buffer = 20
            val firstVisibleIndex = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
            uiListState.value.isNotEmpty() && firstVisibleIndex <= buffer
        }
    }

    val visibleRange = remember {
        derivedStateOf {
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) {
                null
            } else {
                visibleItems.first().index to visibleItems.last().index
            }
        }
    }

    /**
     * ページング処理の発火点
     * NOTE: SnapshotFlowを使うとcollectで発火できるので楽そうでした
     */
    LaunchedEffect(isRequiredMoreLoadAfter.value) {
        if (isRequiredMoreLoadAfter.value) {
            viewModel.loadAfter(30)
        }
    }

    /**
     * 上向きページング処理の発火点
     * prepend は既存要素のインデックスを後ろへずらすが、items に一意なキーを与えているため
     * LazyColumn が「先頭に見えている要素」をキーで追跡してスクロール位置を自動保持する
     */
    LaunchedEffect(isRequiredMoreLoadBefore.value) {
        if (isRequiredMoreLoadBefore.value) {
            viewModel.loadBefore(30)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        PagingStatusHeader(
            itemCount = list.size,
            visibleStart = { visibleRange.value?.first?.let { list.getOrNull(it) } },
            visibleEnd = { visibleRange.value?.second?.let { list.getOrNull(it) } },
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = lazyListState,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = list.size,
                key = { index -> list[index] },
            ) { index ->
                PagingItemRow(
                    value = list[index],
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InfinitescrollappTheme {
        MainScreen()
    }
}
