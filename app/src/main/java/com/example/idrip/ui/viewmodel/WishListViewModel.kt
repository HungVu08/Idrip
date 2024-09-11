package com.example.idrip.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.idrip.models.Product
import com.example.idrip.repository.WishListRepository
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