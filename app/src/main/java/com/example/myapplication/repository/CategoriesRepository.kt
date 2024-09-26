package com.example.myapplication.repository

import com.example.myapplication.api.RetrofitClient

class CategoriesRepository {

    suspend fun getListCategories() = RetrofitClient.getDummyApi.getListCategories()
}