package com.noahseidman.samsclub.delegates

import com.noahseidman.samsclub.di.InjectionsUtils
import com.noahseidman.samsclub.interfaces.PageLoader
import com.noahseidman.samsclub.models.PageModel
import com.noahseidman.samsclub.services.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PageLoaderImpl: PageLoader {

    @Inject
    lateinit var retrofit: RetrofitInstance

    private var pageNumber = 0

    private var isLoading = false

    init {
        InjectionsUtils.components.inject(this)
    }

    private var disposable: Disposable? = null

    override fun loadPage(
        onStart: () -> Unit,
        onFinish: (pageModel: PageModel) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        pageNumber++
        disposable = retrofit.getPage(
            pageNumber = pageNumber,
            pageSize = 20
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                onStart()
                isLoading = true
            }
            .doFinally {
                isLoading = false
            }
            .subscribe({
                onFinish(it)
            }, {
                it.printStackTrace()
                onError(it)
            })
    }

    override fun cancelPageLoad() {
        if (isLoading) {
            pageNumber--
        }
        disposable?.dispose()
    }

    override fun isLoading(): Boolean {
        return isLoading
    }

    override fun restorePage(page: Int) {
        pageNumber = page
    }

    override fun getPage(): Int {
        return pageNumber
    }
}