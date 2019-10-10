package com.noahseidman.samsclub.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(
    val productId: String,
    val productName: String,
    val shortDescription: String?,
    val longDescription: String?,
    val price: String,
    val productImage: String,
    val reviewRating: Float,
    val reviewCount: Int,
    val inStock: Boolean
): Parcelable