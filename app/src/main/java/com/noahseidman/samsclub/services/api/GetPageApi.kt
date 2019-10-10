package com.noahseidman.samsclub.services.api

import com.noahseidman.samsclub.models.PageModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GetPageApi {
    @GET("walmartproducts/{pageNumber}/{pageSize}")
    fun getPage(@Path("pageNumber") pageNumber: Int, @Path("pageSize") pageSize: Int): Observable<PageModel>
}