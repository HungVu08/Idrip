package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.BaseResponse
import com.example.myapplication.models.ListCategoriesResponse
import com.example.myapplication.models.ListProductResponse
import com.example.myapplication.repository.HomeRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CategoriesViewModel(private val homeRepository: HomeRepository,application: Application) : AndroidViewModel(application) {
    val listCategoriesResult : MutableLiveData<BaseResponse<ListCategoriesResponse>> = MutableLiveData()

    init {
        getListCategories()
    }

    fun getListCategories(){
        viewModelScope.launch {
            listCategoriesResult.value = BaseResponse.Loading()
            val result = homeRepository.getListCategories()
            listCategoriesResult.postValue(handlerListCategories(result))

        }
    }

    private fun handlerListCategories(response: Response<ListCategoriesResponse>) : BaseResponse<ListCategoriesResponse>{
        if (response.isSuccessful && response.code() == 200){
            response.body()?.let { it->
                return BaseResponse.Success(it)

            }

        }
        return BaseResponse.Error(null,"Load failed")
    }
}