package com.example.choco.data.repository

import com.example.choco.ChocoApplication
import com.example.choco.data.model.Login
import com.example.choco.data.model.LoginResult
import com.example.choco.utils.Result
import com.example.choco.utils.Constants
import com.example.choco.utils.SettingManager

class LoginRepository(private val remoteDataSource: DataSource) {

    suspend fun login(login: Login): Result<LoginResult> {
        return remoteDataSource.login(login).apply {
            saveToken(this)
        }
    }

    private fun saveToken(result: Result<LoginResult>) {
        if (result is Result.Success)
        {
            SettingManager.init(ChocoApplication.getContext())
            val token = result.value.token
                SettingManager.setValue(Constants().ACCESS_TOKEN, token)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: LoginRepository? = null

        fun getInstance(remoteDataSource: DataSource): LoginRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: LoginRepository(remoteDataSource
                    ).also { INSTANCE = it }
            }
        }
    }
}