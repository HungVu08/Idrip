package com.example.myapplication.models


import com.google.gson.annotations.SerializedName

data class ListCategoriesResponseItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("url")
    val url: String
)