package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Product
import com.example.myapplication.repository.WishListRepository
import kotlinx.coroutines.launch

class WishListViewModel(
    private val wishListRepository: WishListRepository,
    application: Application
) : AndroidViewModel(application) {

    fun upsertWish(product: Product) = viewModelScope.launch {
        wishListRepository.upsertWish(product)
    }

    fun deleteWish(product: Product) = viewModelScope.launch {
        wishListRepository.deleteWish(product)

    }

    fun getAllWishList() = wishListRepository.getAllWishList()
}