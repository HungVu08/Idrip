package com.example.myapplication.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadUrlImage")
    fun ImageView.loadImage(url : String){
        Glide.with(context).load(url).into(this)
    }

}