package com.example.choco.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.choco.data.api.StatusCode.NOT_FOUND
import com.example.choco.data.model.Product
import com.example.choco.data.repository.DataRepository
import com.example.choco.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiStateModel>()
    val uiState: LiveData<UiStateModel> get() = _uiState

    private val _products = MutableLiveData<ArrayList<Product>?>()
    val products: LiveData<ArrayList<Product>?> get() = _products

    private val _notifyItemUpdate = MutableLiveData<Event<Int>>()
    val notifyItemUpdate: LiveData<Event<Int>> get() = _notifyItemUpdate

    private val _updateItemState = MutableLiveData<UiStateModel>()
    val updateItemState: LiveData<UiStateModel> get() = _updateItemState

    fun loadProducts() {
        _uiState.update(loading = true)
        viewModelScope.launch {
            val productList = withContext(Dispatchers.Default) {
                dataRepository.getProducts(SettingManager.getString(Constants().ACCESS_TOKEN).toString())
            }

            _uiState.update(loading = false)

            updateProductList(productList)
        }
    }

    private fun updateProductList(response: ApiResult<ArrayList<Product>>) {
        if (response is ApiResult.Success) {
            _products.value = response.value
            _uiState.update(loading = false)
        } else {
            if (response is ApiResult.Error &&
                response.error?.code == NOT_FOUND) {
                _products.value = null
                _uiState.update(loading = false)
            } else {
                _uiState.handleApiError(response)
            }
        }
    }

}