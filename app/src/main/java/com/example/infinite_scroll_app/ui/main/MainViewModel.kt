package com.example.infinite_scroll_app.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel() : ViewModel() {
    private val _uiList = MutableStateFlow<List<Long>>((0L..30).toList())
    val uiList: StateFlow<List<Long>> = _uiList.asStateFlow()

    val useCase = MainUseCase()

    /**
     * ページング処理
     *
     * @param limit ページングで追加する値
     */
    fun loadAfter(limit: Int) {
        val addList = useCase.makeItems(_uiList.value, limit)
        _uiList.update {
            useCase.addItems(it, addList)
        }
    }
}