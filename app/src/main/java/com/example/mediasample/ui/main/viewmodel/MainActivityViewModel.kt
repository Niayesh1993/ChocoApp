package com.example.mediasample.ui.main.view


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediasample.data.model.Product
import com.example.mediasample.data.repository.MainActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivityViewModel @Inject constructor(private val mainActivityRepository: MainActivityRepository) :
    ViewModel() {

    private val parentJob = SupervisorJob()
    private val _productList = MutableLiveData<List<Product>>()
    private val _status = MutableLiveData<String?>()

    val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + parentJob

    val productList: LiveData<List<Product>>
        get() = _productList
    val status: LiveData<String?>
        get() = _status


    fun loadProductListASynchronously(context: Context) {
        viewModelScope.launch {
            val result =
                runCatching { mainActivityRepository.getListOfProducts(context, "products.json") }
            result.onSuccess {
                _productList.value = it.value!!
            }.onFailure {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancelChildren()
    }
}

