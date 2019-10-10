package com.noahseidman.samsclub.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noahseidman.samsclub.MainActivity
import com.noahseidman.samsclub.ProductDetailsActivity
import com.noahseidman.samsclub.R
import com.noahseidman.samsclub.adapter.MultiTypeDataBoundAdapter
import com.noahseidman.samsclub.databinding.FragmentItemsBinding
import com.noahseidman.samsclub.delegates.PageLoaderImpl
import com.noahseidman.samsclub.extensions.disablePagination
import com.noahseidman.samsclub.extensions.enablePagination
import com.noahseidman.samsclub.interfaces.PageLoader
import com.noahseidman.samsclub.interfaces.ProductCallback
import com.noahseidman.samsclub.models.ProductModel
import com.noahseidman.samsclub.viewmodels.ProductViewModel

class ProductFragment : Fragment(), PageLoader by PageLoaderImpl(), ProductCallback {

    private lateinit var binding: FragmentItemsBinding
    val adapter = MultiTypeDataBoundAdapter(this)

    override fun onProductClick(view: View, productViewModel: ProductViewModel) {
        activity?.let {
            val intent = Intent(view.context, ProductDetailsActivity::class.java)
            intent.putExtra(ProductDetailsActivity.PRODUCT, adapter.items.indexOf(productViewModel))
            intent.putParcelableArrayListExtra(ProductDetailsActivity.PRODUCT_LIST, ArrayList(adapter.items.map { (it as ProductViewModel).productModel }))
            val p1 = androidx.core.util.Pair<View, String>(view.findViewById(R.id.image), "image")
            val p2 = androidx.core.util.Pair<View, String>(view.findViewById(R.id.price), "price")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, p1, p2)
            startActivity(intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentItemsBinding.inflate(inflater)
        binding.recycler.layoutManager = LinearLayoutManager(context!!)
        binding.recycler.adapter = adapter
        (activity as? MainActivity)?.setSupportActionBar(binding.toolbar)
        (activity as? MainActivity)?.supportActionBar?.title = getString(R.string.products)
        (activity as? MainActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as? MainActivity)?.supportActionBar?.setIcon(R.mipmap.ic_launcher)
        //If the fragment is restored from the back stack the state is already retained an available
        if (adapter.itemCount == 0) {
            savedInstanceState?.let {
                restorePage(savedInstanceState.getInt(PAGE))
                adapter.addItems(*savedInstanceState.getParcelableArrayList<ProductModel>(PRODUCTS).map { ProductViewModel(it) }.toTypedArray())
                binding.switcher.displayedChild = 1
            } ?: run {
                loadPageIntl()
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.recycler.enablePagination(::loadPageIntl, ::isLoading)
    }

    override fun onPause() {
        super.onPause()
        binding.recycler.disablePagination()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(PRODUCTS, ArrayList(adapter.items.map { (it as ProductViewModel).productModel }))
        outState.putInt(PAGE, getPage())
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelPageLoad()
    }

    private fun loadPageIntl() {
        loadPage({
            binding.progress.visibility = View.VISIBLE
        }, {
            binding.progress.visibility = View.GONE
            adapter.addItems(*it.products.toList().map { ProductViewModel(it) }.toTypedArray())
            if (binding.switcher.displayedChild == 0) binding.switcher.displayedChild = 1
        }, {
            binding.progress.visibility = View.GONE
            context?.let {
                Toast.makeText(it, R.string.error_loading_page, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val TAG = "ProductFragment"
        const val PRODUCTS = "PRODUCTS"
        const val PAGE = "PAGE"
        const val VISIBLE_THRESHOLD = 10

        fun show(activity: FragmentActivity) {
            val itemsFragment = ProductFragment()
            activity.supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.container,
                    itemsFragment,
                    TAG
                )
                .addToBackStack(TAG)
                .commit()
        }
    }
}