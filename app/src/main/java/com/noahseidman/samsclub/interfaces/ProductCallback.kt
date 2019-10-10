package com.noahseidman.samsclub.interfaces

import android.view.View
import com.noahseidman.samsclub.viewmodels.ProductViewModel

interface ProductCallback: ActionCallback {
    fun onProductClick(view: View, productViewModel: ProductViewModel)
}