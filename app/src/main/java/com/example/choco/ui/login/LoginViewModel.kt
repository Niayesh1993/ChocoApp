package com.example.choco.ui.login

import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.*
import com.example.choco.R
import com.example.choco.data.api.unAuthorized
import com.example.choco.data.model.Login
import com.example.choco.data.repository.DataRepository
import com.example.choco.utils.*
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _usernameError = MutableLiveData<String?>()
    val usernameError: LiveData<String?> get() = _usernameError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    private val _loginBtnEnable = MutableLiveData<Boolean>(false)
    val loginBtnEnable: LiveData<Boolean> get() = _loginBtnEnable

    private val _proceedToApp = MutableLiveData<Event<Boolean>>()
    val proceedToApp: LiveData<Event<Boolean>> get() = _proceedToApp

    private val _uiState = MutableLiveData<UiStateModel>()
    val uiState: LiveData<UiStateModel> get() = _uiState



    fun login(username: EditText, password: EditText) {
        viewModelScope.launch {
            _uiState.update(loading = true)
            var login = Login()
            login.email = username.text.toString()
            login.password = password.text.toString()

            val response = dataRepository.login(login)
            withContext(Dispatchers.Main) {

                if (response != null)
                {
                    if (response is ApiResult.Success)
                    {
                        print(response)
                        var token = response.value.token
                        SettingManager.setValue(Constants().ACCESS_TOKEN, token)
                        _proceedToApp.value = Event(true)
                    }

                }
                else if (response is ApiResult.Error) {

                    if (response.error.unAuthorized()) _usernameError.value = response.error?.text
                    else _uiState.update(error = Event(response.error ?: ApiError()))

                }else if (response is ApiResult.NetworkError) {
                    _uiState.update(toast = Event(R.string.no_internet_access.toString()))
                }

                _uiState.update(loading = false)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (InputHelper.isEmpty(username)) {
            _usernameError.value = R.string.enter_email_error.toString()
        } else {
            _usernameError.value = null
        }

        if (!InputHelper.isEmpty(password)) {
            _passwordError.value = null
        }

        _loginBtnEnable.value =
            !InputHelper.isEmpty(password) &&
                    !InputHelper.isEmpty(username)
    }

    @BindingAdapter("app:errorText")
    fun setErrorText(textInputLayout: TextInputLayout, errorMessage: String?) {
        if (InputHelper.isEmpty(errorMessage))
            textInputLayout.error = null
        else
            textInputLayout.error = errorMessage
    }
}