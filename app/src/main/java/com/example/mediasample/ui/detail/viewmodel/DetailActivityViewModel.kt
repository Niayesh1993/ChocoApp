package com.example.mediasample.ui.detail.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediasample.data.model.Product
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailActivityViewModel @Inject constructor() : ViewModel() {

    private val _product = MutableLiveData<Product>()
    private val _snackbar = MutableLiveData<String?>()

    val product: LiveData<Product>
        get() = _product
    val snackbar: LiveData<String?>
        get() = _snackbar

    fun onSnackbarShown(favorite: Boolean) {
        if (favorite) _snackbar.value = "Add Favorite"
        else _snackbar.value = "Remove Favorite"
    }

    //fetch data from the intent
    fun fetchProduct(intent: Intent) {
        viewModelScope.launch {
            val result = runCatching { intent.getSerializableExtra("product") as Product }
            result.onSuccess {
                _product.value = it
            }.onFailure {
            }
        }
    }

}