package com.example.myapplication.utils

object StringUtils {
    @JvmStatic
    fun convertDouble2String(input : Double) = input.toString()

    @JvmStatic
    fun convertDola2String(input: Double) = "$$input"
}