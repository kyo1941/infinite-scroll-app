package com.example.infinite_scroll_app.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infinite_scroll_app.ui.theme.InfinitescrollappTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val viewModel: MainViewModel = viewModel()
    val list = viewModel.uiList.collectAsState()
    val listState = rememberLazyListState()

    /**
     * 下側にbuffer(=20件)よりも下回った場合にページング処理を行う
     */
    val isRequiredMoreLoadAfter = remember {
        derivedStateOf {
            val buffer = 20
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            list.value.size - lastVisibleIndex <= buffer
        }
    }

    /**
     * 上側にbuffer(=20件)よりも近づいた場合に上向きページング処理を行う
     * NOTE: 上端はインデックス0で固定なので、先頭可視インデックスそのものが先頭からの距離になる
     */
    val isRequiredMoreLoadBefore = remember {
        derivedStateOf {
            val buffer = 20
            val firstVisibleIndex = listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
            list.value.isNotEmpty() && firstVisibleIndex <= buffer
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

    LazyColumn(
        modifier = modifier.padding(start = 8.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(
            count = list.value.size,
            key = { index -> list.value[index] },
        ) {
            Text(
                text = list.value[it].toString(),
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )
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
