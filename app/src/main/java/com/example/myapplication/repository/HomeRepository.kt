package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitClient

class HomeRepository {

    suspend fun getListProduct() = RetrofitClient.getDummyApi.getListProduct()

    suspend fun getListProduct(limit : Int) = RetrofitClient.getDummyApi.getListProduct(limit)

    suspend fun getListCategories() = RetrofitClient.getDummyApi.getListCategories()

}