package com.noahseidman.samsclub

import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noahseidman.samsclub.delegates.PageLoaderImpl
import com.noahseidman.samsclub.di.InjectionsUtils
import com.noahseidman.samsclub.fragments.ProductFragment
import com.noahseidman.samsclub.models.ProductModel
import com.noahseidman.samsclub.robosupport.NetworkSecurityPolicyShadow
import com.noahseidman.samsclub.robosupport.ShadowViewPager
import com.noahseidman.samsclub.robosupport.ViewCompatShadow
import com.noahseidman.samsclub.viewmodels.ProductViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@Config(
    sdk = [27],
    shadows = [ShadowViewPager::class, ViewCompatShadow::class, NetworkSecurityPolicyShadow::class],
    application = TestApplication::class
)
class ExampleUnitTest {

    companion object {
        init {
            InjectionsUtils.setTestComponents(DaggerTestComponents.builder().context(ApplicationProvider.getApplicationContext()).build())
            RxJavaPlugins.setIoSchedulerHandler { AndroidSchedulers.mainThread() }
            RxJavaPlugins.setComputationSchedulerHandler { AndroidSchedulers.mainThread() }
        }
    }


    @Test
    fun productFragment() {
        val fragment = FragmentScenario.launch(ProductFragment::class.java)
        fragment.onFragment {
            check(it.adapter.items.isNotEmpty())
        }
    }

    @Test
    fun pageLoader() {
        val pageLoader = PageLoaderImpl()
        pageLoader.loadPage({}, {}, {})
        check(pageLoader.getPage() == 1)
        pageLoader.cancelPageLoad()
        check(pageLoader.getPage() == 0)
        check(!pageLoader.isLoading())
        pageLoader.restorePage(5)
        check(pageLoader.getPage() == 5)
    }

    @Test
    fun productViewModel() {
        val model = ProductModel(
            "",
            "",
            "",
            "",
            "",
            "",
            0.0F,
            0,
            true)
        val productViewModel = ProductViewModel(model)
        check(productViewModel.getRatingCount().contains("0.0"))
        check(productViewModel.getReviewCount().contains("0"))
        check(productViewModel.getInStock().toLowerCase().contains("yes"))
    }
}
