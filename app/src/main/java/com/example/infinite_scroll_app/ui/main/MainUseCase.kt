package com.example.infinite_scroll_app.ui.main

class MainUseCase() {
    /**
     * 最終インデックスから延長する連番の配列を生成する
     *
     * @param list 最終インデックスを取得する配列
     * @param limit 生成する最大値
     */
    fun makeItemsAfter(list: List<Long>, limit: Int): List<Long> {
        val last = list.lastOrNull()
        val start = if (last == null) 0L else last + 1
        return (start until start + limit).toList()
    }

    /**
     * 先頭インデックスから遡る連番の配列を生成する
     *
     * @param list 先頭インデックスを取得する配列
     * @param limit 生成する最大値
     */
    fun makeItemsBefore(list: List<Long>, limit: Int): List<Long> {
        val first = list.firstOrNull() ?: 0L
        return (first - limit until first).toList()
    }

    /**
     * 配列の末尾に新しい配列を追加する
     *
     * @param baseList 追加される前の配列
     * @param addList 末尾に追加する配列
     */
    fun addItemsAfter(baseList: List<Long>, addList: List<Long>): List<Long> {
        return baseList.plus(addList)
    }

    /**
     * 配列の先頭に新しい配列を追加する
     *
     * @param baseList 追加される前の配列
     * @param addList 先頭に追加する配列
     */
    fun addItemsBefore(baseList: List<Long>, addList: List<Long>): List<Long> {
        return addList.plus(baseList)
    }
}
