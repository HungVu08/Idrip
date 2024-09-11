package com.example.idrip.database.wishdb

import androidx.room.TypeConverter

class WishConverter {
    @TypeConverter
    fun fromImage(data :List<String>) : String{
        var result = ""
        for (i in data.indices){
            result += data[i]
            if (i < data.size -1){
                result += ","
            }

        }
            return result
    }

    @TypeConverter
    fun toImage (data : String) : List<String>{
        return data.split(",")
    }

}