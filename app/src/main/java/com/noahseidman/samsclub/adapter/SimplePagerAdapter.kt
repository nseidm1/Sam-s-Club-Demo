package com.noahseidman.samsclub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.noahseidman.samsclub.databinding.ProductDetailsViewBinding
import com.noahseidman.samsclub.interfaces.ImageLoadedCallback
import com.noahseidman.samsclub.viewmodels.ProductViewModel


class SimplePagerAdapter(
    private val activity: FragmentActivity,
    private val imageLoadedCallback: ImageLoadedCallback,
    private val pages: List<ProductViewModel>,
    private val targetProduct: Int
) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val binding = ProductDetailsViewBinding.inflate(LayoutInflater.from(activity))
        if (position == targetProduct) {
            binding.image.transitionName = "image"
            binding.price.transitionName = "price"
            binding.callback = imageLoadedCallback
        }
        binding.data = pages[position]
        collection.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}