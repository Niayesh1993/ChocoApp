package com.example.choco.ui.login

import androidx.lifecycle.*
import com.example.choco.R
import com.example.choco.data.model.Login
import com.example.choco.data.repository.LoginRepository
import com.example.choco.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
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



    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.update(loading = true)
            val login = Login(username, password)

            val response = loginRepository.login(login)
            withContext(Dispatchers.Main) {

                if (response is ApiResult.Success)
                {
                    _proceedToApp.value = Event(true)
                }

                _uiState.update(loading = false)
            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (username.isEmpty()) {
            _usernameError.value = R.string.enter_email_error.toString()
        } else {
            _usernameError.value = null
        }

        if (password.isNotEmpty()) {
            _passwordError.value = null
        }

        _loginBtnEnable.value =
            password.isNotEmpty() &&
                    username.isNotEmpty()
    }

}