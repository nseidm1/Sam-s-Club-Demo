package com.noahseidman.samsclub.viewmodels

import android.animation.LayoutTransition
import android.content.Context
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.noahseidman.samsclub.R
import com.noahseidman.samsclub.adapter.DataBoundViewHolder
import com.noahseidman.samsclub.adapter.DynamicBinding
import com.noahseidman.samsclub.adapter.LayoutBinding
import com.noahseidman.samsclub.databinding.ProductViewBinding
import com.noahseidman.samsclub.di.InjectionsUtils
import com.noahseidman.samsclub.models.ProductModel
import javax.inject.Inject

class ProductViewModel(
    val productModel: ProductModel
) : BaseObservable(), LayoutBinding, DynamicBinding {

    @Inject
    lateinit var context: Context

    init {
        InjectionsUtils.components.inject(this)
    }

    override fun bind(holder: DataBoundViewHolder<*>) {
         (holder.binding as ProductViewBinding).leftContainer.getLayoutTransition()
            .enableTransitionType(LayoutTransition.CHANGING);
    }

    override val layoutId: Int
        get() = R.layout.product_view

    @Bindable
    fun getProductName(): String {
        return productModel.productName
    }

    @Bindable
    fun getProductImage(): String {
        return "${context.getString(R.string.base_url)}${productModel.productImage.substring(1)}"
    }

    @Bindable
    fun getLongDescription(): Spanned {
        productModel.longDescription?.let {
            return Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        } ?: run {
            return SpannableString("")
        }
    }

    @Bindable
    fun getShortDescription(): Spanned {
        productModel.shortDescription?.let {
            return Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
        } ?: run {
            return SpannableString("")
        }
    }

    @Bindable
    fun getProductId(): String {
        return productModel.productId
    }

    @Bindable
    fun getPrice(): String {
        return productModel.price
    }

    @Bindable
    fun getRatingCount(): String {
        return String.format(context.getString(R.string.ratings), productModel.reviewRating)
    }

    @Bindable
    fun getReviewCount(): String {
        return String.format(context.getString(R.string.reviews), productModel.reviewCount)
    }

    @Bindable
    fun getInStock(): String {
        return String.format(
            context.getString(
                R.string.in_stock,
                if (productModel.inStock) context.getString(R.string.yes).capitalize() else context.getString(R.string.no)
            ).capitalize()
        )
    }
}