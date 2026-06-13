package com.example.infinite_scroll_app.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel() : ViewModel() {
    private val _uiList = MutableStateFlow<List<Long>>(emptyList())
    val uiList: StateFlow<List<Long>> = _uiList.asStateFlow()

    val useCase = MainUseCase()

    /**
     * ページング処理
     *
     * @param limit ページングで追加する値
     */
    fun loadAfter(limit: Int) {
        val addList = useCase.makeItemsAfter(_uiList.value, limit)
        _uiList.update {
            useCase.addItemsAfter(it, addList)
        }
    }

    /**
     * 上向きページング処理
     *
     * リストの先頭に新しい連番を prepend する。prepend は既存要素のインデックスを
     * ずらすが、スクロール位置の保持は LazyColumn のアイテムキーによる自動アンカリングに任せる。
     *
     * @param limit ページングで先頭に追加する個数
     */
    fun loadBefore(limit: Int) {
        if (_uiList.value.isEmpty()) return
        val addList = useCase.makeItemsBefore(_uiList.value, limit)
        _uiList.update {
            useCase.addItemsBefore(it, addList)
        }
    }
}