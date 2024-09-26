package com.example.myapplication.database.wishdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.models.Product
import com.example.myapplication.utils.Constants

@Database(entities = [Product::class], version = 1)
@TypeConverters(WishConverter::class)
abstract class WishListDatabase : RoomDatabase() {

    abstract fun getWishDao() : WishDao

    companion object{
        private var instance : WishListDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?:createDatabase(context).also {it->
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(context,WishListDatabase::class.java, Constants.DB_NAME).build()
    }
}