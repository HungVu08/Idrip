package com.example.myapplication.api

import com.example.myapplication.models.ListCategoriesResponse
import com.example.myapplication.models.ListProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyApi {

    @GET("/products")
    suspend fun getListProduct() : Response<ListProductResponse>

    @GET("/products")
    suspend fun getListProduct(@Query("limit") limit :Int): Response<ListProductResponse>

    @GET("/products/categories")
    suspend fun getListCategories() : Response<ListCategoriesResponse>
}