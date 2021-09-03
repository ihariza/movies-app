package com.nhariza.moviesapp.view.common

class Pager {
    companion object {
        private const val PAGE_LIMIT = 20
        private const val PAGE_THRESHOLD = 5
    }

    val page: Int
        get() = _page + 1
    private var _page = 0

    var isLoading = false

    private var pageLimit = false

    fun requireNewPage(lastVisible: Int, totalItems: Int): Boolean {
        if (!isLoading && !pageLimit && (lastVisible >= totalItems - PAGE_THRESHOLD)) {
            isLoading = true
            return true
        }
        return false
    }

    fun setPageLoaded(newItemsSize: Int) {
        isLoading = false
        if (newItemsSize >= PAGE_LIMIT) {
            _page++
        } else {
            pageLimit = true
        }
    }

    fun setPageError() {
        isLoading = false
    }

    fun reset() {
        _page = 0
        pageLimit = false
        isLoading = false
    }
}