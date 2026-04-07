package com.example.infinite_scroll_app.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    private val mutableList = (0L..30L).toMutableList()
    val uiList: List<Long> = mutableList

    val useCase = MainUseCase()

    /**
     * ページング処理
     *
     * @param limit ページングで追加する値
     */
    fun reload(limit: Int) {
        val addList = useCase.makeItems(mutableList, limit)
        useCase.addItems(mutableList, addList)
    }
}