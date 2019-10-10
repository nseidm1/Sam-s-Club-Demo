package com.noahseidman.samsclub.models

data class PageModel(
    val products: Array<ProductModel>,
    val totalProducts: Int,
    val pageSize: Int,
    val statusCode: Int
)

