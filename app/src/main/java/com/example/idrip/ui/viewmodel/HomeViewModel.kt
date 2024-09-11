package com.example.idrip.ui.viewmodel

import android.app.Application
import android.media.audiofx.DynamicsProcessing.Limiter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.idrip.api.BaseResponse
import com.example.idrip.models.ListProductResponse
import com.example.idrip.repository.HomeRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val homeRepository: HomeRepository, application: Application) :
    AndroidViewModel(application) {

    val listProductResult: MutableLiveData<BaseResponse<ListProductResponse>> = MutableLiveData()

    init {
        getListProductWithLimit( 0)
    }
    fun getListProduct() {
        viewModelScope.launch {
            listProductResult.value = BaseResponse.Loading()
            val result = homeRepository.getListProduct()
            listProductResult.postValue(handlerListProduct(result))
        }
    }    fun getListProductWithLimit(limit : Int) {
        viewModelScope.launch {
            listProductResult.value = BaseResponse.Loading()
            val result = homeRepository.getListProduct(limit)
            listProductResult.postValue(handlerListProduct(result))
        }
    }

    private fun handlerListProduct(response: Response<ListProductResponse>): BaseResponse<ListProductResponse> {
        if (response.isSuccessful && response.code() == 200) {
            response.body()?.let {
                return BaseResponse.Loading()
            }
        }
        return BaseResponse.Error(null, "Load Failed")
    }


}