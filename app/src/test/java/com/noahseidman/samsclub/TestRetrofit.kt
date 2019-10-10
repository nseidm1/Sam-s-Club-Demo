package com.noahseidman.samsclub

import com.appham.mockinizer.Method
import com.appham.mockinizer.RequestFilter
import com.appham.mockinizer.mockinize
import com.google.gson.Gson
import com.noahseidman.samsclub.models.PageModel
import com.noahseidman.samsclub.models.ProductModel
import com.noahseidman.samsclub.services.RetrofitInstance
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun RetrofitInstance.addMock() {
    val mocks: Map<RequestFilter, MockResponse> = mapOf(
        RequestFilter(
            path = "/walmartproducts/1/20",
            method = Method.GET,
            body = null
        ) to MockResponse().apply {
            setResponseCode(200)
            val productModelList = mutableListOf<ProductModel>()
            for (i in 1..20) {
                productModelList.add(ProductModel(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    0.0F,
                    0,
                    true)

                )
            }
            val pageModel = PageModel(
                productModelList.toTypedArray(),
                0,
                0,
                0
            )
            setBody(Gson().toJson(pageModel))
        }
    )

    val okClient = OkHttpClient.Builder().dispatcher(Dispatcher(SameThreadExecutorService())).mockinize(mocks).build()

    client = retrofit2.Retrofit.Builder()
        .baseUrl(context.getString(R.string.base_url))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .callbackExecutor { it.run() }
        .client(okClient).build()
}