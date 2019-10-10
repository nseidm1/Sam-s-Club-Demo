package com.noahseidman.samsclub.adapter

import androidx.annotation.LayoutRes

interface LayoutBinding {

    /**
     * Get the layout resource ID for an view that needs to be bound.
     *
     * @return the resource ID of the layout
     */
    @get:LayoutRes
    val layoutId: Int
}