package com.example.idrip.utils

object StringUtils {
    @JvmStatic
    fun convertDouble2String(input: Double) = input.toString()

    @JvmStatic
    fun convertDouble2Dola(input: Double) = "$$input"

}