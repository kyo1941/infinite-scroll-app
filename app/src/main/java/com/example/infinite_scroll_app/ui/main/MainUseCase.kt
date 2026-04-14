package com.example.infinite_scroll_app.ui.main

class MainUseCase() {
    /**
     * 最終インデックスから延長する連番の配列を生成する
     *
     * @param list 最終インデックスを取得する配列
     * @param limit 生成する最大値
     */
    fun makeItems(list: List<Long>, limit: Int): List<Long> {
        val last = list.lastOrNull() ?: 0L
        return (last..last + limit).toList()
    }

    /**
     * 配列に新しい配列を追加する
     *
     * @param baseList 追加される前の配列
     * @param addList 追加する配列
     */
    fun addItems(baseList: List<Long>, addList: List<Long>): List<Long> {
        return baseList.plus(addList)
    }
}
