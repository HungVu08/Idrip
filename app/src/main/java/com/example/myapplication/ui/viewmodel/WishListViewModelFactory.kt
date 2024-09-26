package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.WishListRepository

class WishListViewModelFactory(
    private val wishListRepository: WishListRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WishListViewModel(wishListRepository, application) as T
    }
}