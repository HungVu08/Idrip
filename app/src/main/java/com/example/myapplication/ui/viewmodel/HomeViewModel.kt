package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.BaseResponse
import com.example.myapplication.models.ListProductResponse
import com.example.myapplication.repository.HomeRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val homeRepository: HomeRepository, application: Application) :
    AndroidViewModel(application) {

     val listProductResult: MutableLiveData<BaseResponse<ListProductResponse>> = MutableLiveData()
    private var listProductResponse : ListProductResponse? = null

    init {
        getListProductWithLimit(0)
    }
    fun getListProduct(){
        viewModelScope.launch {
            listProductResult.value = BaseResponse.Loading()
            val result = homeRepository.getListProduct()
            listProductResult.postValue(handlerListProduct(result))
        }
    }

    fun getListProductWithLimit(limit : Int) {
        viewModelScope.launch {
            listProductResult.value = BaseResponse.Loading()
            val result = homeRepository.getListProduct(limit)
            listProductResult.postValue(handlerListProduct(result))
        }
    }

    private fun handlerListProduct(response: Response<ListProductResponse>) : BaseResponse<ListProductResponse>{
        if (response.isSuccessful && response.code() == 200){
            response.body()?.let { it->
                listProductResponse = it
                return BaseResponse.Success(it)

            }

        }
        return BaseResponse.Error(null,"Load failed")
    }


}