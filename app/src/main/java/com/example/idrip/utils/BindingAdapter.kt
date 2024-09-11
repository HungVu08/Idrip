package com.example.idrip.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun ImageView.loadUrlImage(url : String){
        Glide.with(context).load(url).into(this)
    }

}