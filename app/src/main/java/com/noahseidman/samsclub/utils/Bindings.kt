package com.noahseidman.samsclub.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.noahseidman.samsclub.interfaces.ImageLoadedCallback
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("imageUrl")
fun loadImageUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl == null) {
        return
    }
    Picasso.get().load(imageUrl).into(view)
}

@BindingAdapter("imageUrl", "callback")
fun loadImageUrl(view: ImageView, imageUrl: String?, callback: ImageLoadedCallback?) {
    if (imageUrl == null) {
        return
    }
    Picasso.get().load(imageUrl).into(view, object : Callback {
        override fun onSuccess() {
            callback?.onImageLoaded()
        }

        override fun onError(e: Exception?) {
            callback?.onImageLoaded()
        }
    })
}