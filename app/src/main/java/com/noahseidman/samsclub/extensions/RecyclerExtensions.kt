package com.noahseidman.samsclub.extensions

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noahseidman.samsclub.fragments.ProductFragment

fun RecyclerView.enablePagination(loadPage: () -> Unit, isLoading: () -> Boolean) {

    var lastVisibleItem: Int
    var totalItemCount: Int

    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            totalItemCount = layoutManager?.itemCount ?: 0
            lastVisibleItem =
                (layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: 0
            if (!isLoading() && lastVisibleItem >= totalItemCount - ProductFragment.VISIBLE_THRESHOLD) {
                loadPage()
            }
        }
    }
    addOnScrollListener(onScrollListener)
    tag = onScrollListener
}

fun RecyclerView.disablePagination() {
    tag?.let {
        if (it is AbsListView.OnScrollListener) {
            removeOnScrollListener(it as RecyclerView.OnScrollListener)
        }
    }
}