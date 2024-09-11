package com.example.idrip.repository

import com.example.idrip.database.wishdb.WishListDatabase
import com.example.idrip.models.Product

class WishListRepository(private val wishListDatabase: WishListDatabase) {

    suspend fun upsertWish(product: Product) = wishListDatabase.getWishDao().upsertWish(product)

    suspend fun deleteWish(product: Product) = wishListDatabase.getWishDao().deleteWishList(product)

    fun getAllWishList() = wishListDatabase.getWishDao().getAllWishList()


}