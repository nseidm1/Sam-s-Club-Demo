/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.noahseidman.samsclub.adapter

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.OnRebindCallback
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * A reference implementation for an adapter that wants to use data binding "the right way". It
 * works with [DataBoundViewHolder].
 *
 * It listens for layout invalidations and notifies RecyclerView about them before views refresh
 * themselves. It also avoids invalidating the full item when data in the bound item dispatches
 * proper notify events.
 *
 * This class uses layout id as the item type.
 *
 * It can be used for both single type lists and multiple type lists.
 *
 * @param <T> The type of the ViewDataBinding class. Can be ommitted in multiple-binding-type use
 * case.
</T> */
abstract class BaseDataBoundAdapter<T : ViewDataBinding> : RecyclerView.Adapter<DataBoundViewHolder<T>>() {
    private var mRecyclerView: RecyclerView? = null

    /**
     * This is used to block items from updating themselves. RecyclerView wants to know when an
     * item is invalidated and it prefers to refresh it via onRebind. It also helps with performance
     * since data binding will not update views that are not changed.
     */
    private val mOnRebindCallback = object : OnRebindCallback<ViewDataBinding?>() {
        override fun onPreBind(binding: ViewDataBinding?): Boolean {
            if (mRecyclerView == null || mRecyclerView!!.isComputingLayout) {
                return true
            }
            val childAdapterPosition = mRecyclerView!!.getChildAdapterPosition(binding!!.root)
            if (childAdapterPosition == RecyclerView.NO_POSITION) {
                return true
            }
            notifyItemChanged(childAdapterPosition, DB_PAYLOAD)
            return false
        }
    }

    @CallSuper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<T> {
        val vh = DataBoundViewHolder.create<T>(parent, viewType)
        vh.binding.addOnRebindCallback(mOnRebindCallback)
        return vh
    }

    override fun onBindViewHolder(
        holder: DataBoundViewHolder<T>, position: Int,
        payloads: List<Any>
    ) {
        // when a VH is rebound to the same item, we don't have to call the setters
        if (payloads.isEmpty() || hasNonDataBindingInvalidate(payloads)) {
            bindItem(holder, position, payloads)
        }
        holder.binding.executePendingBindings()
    }

    /**
     * Override this method to handle binding your items into views
     *
     * @param holder   The ViewHolder that has the binding instance
     * @param position The position of the item in the adapter
     * @param payloads The payloads that were passed into the onBind method
     */
    protected abstract fun bindItem(
        holder: DataBoundViewHolder<T>, position: Int,
        payloads: List<Any>
    )

    private fun hasNonDataBindingInvalidate(payloads: List<Any>): Boolean {
        for (payload in payloads) {
            if (payload !== DB_PAYLOAD) {
                return true
            }
        }
        return false
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<T>, position: Int) {
        throw IllegalArgumentException("just overridden to make final.")
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = null
    }

    override fun getItemViewType(position: Int): Int {
        return getItemLayoutId(position)
    }

    @LayoutRes
    abstract fun getItemLayoutId(position: Int): Int

    companion object {

        private val DB_PAYLOAD = Any()
    }
}