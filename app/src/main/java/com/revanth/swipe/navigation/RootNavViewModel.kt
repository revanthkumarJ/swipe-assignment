package com.revanth.swipe.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revanth.swipe.core.network.ApiService
import kotlinx.coroutines.launch

class RootNavViewModel(
    private val apiService: ApiService
) : ViewModel() {

    init {
        getProducts()
    }

    private fun getProducts(){
        viewModelScope.launch {
            val res=apiService.getProducts()
            Log.d("RootNavViewModel", "getProducts: $res")
        }
    }
}