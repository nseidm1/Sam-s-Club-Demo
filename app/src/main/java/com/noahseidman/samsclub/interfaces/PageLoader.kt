package com.noahseidman.samsclub.interfaces

import com.noahseidman.samsclub.models.PageModel

interface PageLoader {

    fun loadPage(onStart: () -> Unit, onFinish: (pageMode: PageModel) -> Unit, onError: (t: Throwable) -> Unit)
    fun cancelPageLoad()
    fun isLoading(): Boolean
    fun restorePage(page: Int)
    fun getPage(): Int
}