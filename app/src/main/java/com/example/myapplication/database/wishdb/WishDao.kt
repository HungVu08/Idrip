package com.example.myapplication.database.wishdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.models.Product
@Dao
interface WishDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWish(product: Product)

    @Delete
    suspend fun deleteWish(product: Product)

    @Query("SELECT * FROM Product")
    fun getAllWishList() : LiveData<List<Product>>

}