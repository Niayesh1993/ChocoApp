package com.example.choco.utils

import android.app.Activity
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.choco.ui.widget.MaterialSnackBar
import com.example.choco.R

fun AppCompatActivity.observeUiState(uiStateModel: LiveData<UiStateModel>) {
    uiStateModel.observe(this, Observer {
        val uiModel = it ?: return@Observer

        updateUiState(uiModel, this)
    })
}

fun Fragment.observeUiState(uiState: LiveData<UiStateModel>) {
    uiState.observe(this, Observer {
        val uiModel = it ?: return@Observer
        val activity = requireActivity()

        updateUiState(uiModel, activity)
    })
}

fun MutableLiveData<UiStateModel>.update(loading: Boolean = false,
                                         error: Event<ApiError>? = null,
                                         toast: Event<String>? = null,
                                         snackBar: Event<String>? = null,
                                         finish: Event<Boolean>? = null) {
    val uiStateModel = UiStateModel(loading, error, toast, snackBar, finish)
    if(Looper.myLooper()== Looper.getMainLooper()){
        value = uiStateModel
    } else{
        postValue(uiStateModel)
    }
}

fun <T> MutableLiveData<UiStateModel>.handleApiError(response: Result<T>) {
    if (response is Result.Error) {
        update(error = Event(response.error ?: ApiError()))
    }

    if (response is Result.NetworkError) {
        update(toast = Event(R.string.no_internet_access.toString()))
    }
}

fun updateUiState(uiModel: UiStateModel, activity: Activity) {

    uiModel.error?.getContentIfNotHandled()?.apply {

    }

    uiModel.toast?.getContentIfNotHandled()?.apply {
        activity.showToast(this)
    }

    uiModel.snackBar?.getContentIfNotHandled()?.apply {
        activity.showSnackbar(this)
    }

    uiModel.finish?.getContentIfNotHandled()?.apply{
        if (this) {
            activity.finish()
        }
    }
}

fun Activity.showToast(text: String?) {
    MessageViewer.startToast(this, text)
}

fun Activity.showAlertDialog(text: String, clickListener: MessageViewer.DialogClickListener) {
    MessageViewer.startAlertDialog(this, text, clickListener)
}

fun Activity.showSnackbar(text: String?) {
    if (text != null) {
        MaterialSnackBar.make(findViewById(android.R.id.content), text).show()
    }
}


data class UiStateModel(
    val loading: Boolean,
    val error: Event<ApiError>?,
    val toast: Event<String>?,
    val snackBar: Event<String>?,
    val finish: Event<Boolean>?
)
