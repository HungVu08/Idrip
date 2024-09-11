package com.example.idrip.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.idrip.repository.HomeRepository

class HomeViewModelFactory(
    private val homeRepository: HomeRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepository, application)  as T
    }
}