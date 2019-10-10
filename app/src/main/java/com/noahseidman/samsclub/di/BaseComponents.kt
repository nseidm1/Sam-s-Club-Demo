package com.noahseidman.samsclub.di

import com.noahseidman.samsclub.delegates.PageLoaderImpl
import com.noahseidman.samsclub.viewmodels.ProductViewModel

interface BaseComponents {
    fun inject(pageLoaderImpl: PageLoaderImpl)
    fun inject(productViewModel: ProductViewModel)
}