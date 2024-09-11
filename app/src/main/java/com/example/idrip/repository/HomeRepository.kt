package com.example.idrip.repository

import com.example.idrip.api.RetrofitClient

class HomeRepository {

    suspend fun getListProduct() = RetrofitClient.getDummyApi.getListProduct()

    suspend fun getListProduct(limit : Int) = RetrofitClient.getDummyApi.getListProduct(limit)

}