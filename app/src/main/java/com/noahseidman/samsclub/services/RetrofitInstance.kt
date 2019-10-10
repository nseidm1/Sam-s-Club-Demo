package com.noahseidman.samsclub.services

import android.content.Context
import com.noahseidman.samsclub.R
import com.noahseidman.samsclub.models.PageModel
import com.noahseidman.samsclub.services.api.GetPageApi
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance(val context: Context) {

    var client: Retrofit

    init {
        val builder = Retrofit.Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        client = builder.build()
    }

    fun getPage(pageNumber: Int, pageSize: Int): Observable<PageModel> {
        val mediaService = client.create(GetPageApi::class.java)
        return mediaService.getPage(pageNumber, pageSize)
    }
}