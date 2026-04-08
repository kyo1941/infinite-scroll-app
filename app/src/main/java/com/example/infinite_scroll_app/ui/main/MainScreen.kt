package com.example.infinite_scroll_app.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

    LazyColumn(
        modifier = modifier.padding(start = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        items(list.value.size) {
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
