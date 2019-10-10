package com.noahseidman.samsclub

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.noahseidman.samsclub.adapter.SimplePagerAdapter
import com.noahseidman.samsclub.databinding.ActivityProductDetailsBinding
import com.noahseidman.samsclub.interfaces.ImageLoadedCallback
import com.noahseidman.samsclub.models.ProductModel
import com.noahseidman.samsclub.viewmodels.ProductViewModel

class ProductDetailsActivity : AppCompatActivity(), ImageLoadedCallback {

    private lateinit var binding: ActivityProductDetailsBinding

    private var product = 0
    private lateinit var products: List<ProductViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        product = intent?.extras?.getInt(PRODUCT) ?:
                savedInstanceState?.getInt(PRODUCT) ?: 0
        products = intent?.extras?.getParcelableArrayList<ProductModel>(PRODUCT_LIST)?.map { ProductViewModel(it) } ?:
                savedInstanceState?.getParcelableArrayList<ProductModel>(PRODUCT_LIST)?.map { ProductViewModel(it) } ?: listOf()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.product_description)
        setupPager()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putInt(PRODUCT, product)
        outState?.putParcelableArrayList(PRODUCT_LIST, ArrayList(products.map { it.productModel }))
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.home -> onBackPressed()
            android.R.id.home -> onBackPressed()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onImageLoaded() {
        startPostponedEnterTransition()
    }

    override fun onBackPressed() {
        if (binding.pager.currentItem != product) {
            binding.pager.setCurrentItem(product, true)
            Handler().postDelayed({
                finishAfterTransition()
            }, 650)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupPager() {
        binding.pager.adapter = SimplePagerAdapter(
            activity = this,
            imageLoadedCallback = this,
            pages = products,
            targetProduct = product
        )
        binding.pager.currentItem = product
    }

    companion object {
        const val PRODUCT = "PRODUCT"
        const val PRODUCT_LIST = "PRODUCT_LIST"
    }
}