package com.noahseidman.samsclub.adapter

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

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.noahseidman.samsclub.BR
import com.noahseidman.samsclub.interfaces.ActionCallback

class MultiTypeDataBoundAdapter(
    private val mActionCallback: ActionCallback
) : BaseDataBoundAdapter<ViewDataBinding>() {

    private val mItems = mutableListOf<Any>()

    val items: List<Any>
        get() = mItems

    override fun bindItem(holder: DataBoundViewHolder<ViewDataBinding>, position: Int, payloads: List<Any>) {
        val item = mItems[position]
        holder.binding.setVariable(BR.data, mItems[position])
        // this will work even if the layout does not have a callback parameter
        holder.binding.setVariable(BR.callback, mActionCallback)
        (item as? DynamicBinding)?.bind(holder)
    }

    @LayoutRes
    override fun getItemLayoutId(position: Int): Int {
        // use layout ids as types
        val item = getItem(position)
        return when (item) {
            is LayoutBinding -> item.layoutId
            else -> throw IllegalArgumentException("unknown item type " + item!!)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    private fun getItem(position: Int): Any? {
        return if (position < mItems.size) mItems[position] else null
    }

    fun addItems(vararg items: Any) {
        val start = mItems.size
        mItems.addAll(items)
        notifyItemRangeChanged(start, items.size)
    }
}