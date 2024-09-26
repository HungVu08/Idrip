package com.example.myapplication.repository

import com.example.myapplication.database.wishdb.WishListDatabase
import com.example.myapplication.models.Product

class WishListRepository(private val wishListDatabase: WishListDatabase){

    suspend fun upsertWish(product: Product) = wishListDatabase.getWishDao().upsertWish(product)

    suspend fun deleteWish(product: Product) = wishListDatabase.getWishDao().deleteWish(product)

    fun getAllWishList() = wishListDatabase.getWishDao().getAllWishList()
}