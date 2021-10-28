package com.example.choco.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.example.choco.ui.widget.MaterialSnackBar
import com.example.choco.databinding.ActivityLoginBinding
import com.example.choco.ui.main.MainActivity
import com.example.choco.utils.EventObserver
import com.example.choco.utils.observeUiState
import com.example.choco.utils.viewbinding.viewBindings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private val binding: ActivityLoginBinding by viewBindings()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        observeUiState(viewModel.uiState)

        binding.username.doAfterTextChanged {
            viewModel.loginDataChanged(it.toString(), binding.password.text.toString())
        }
        binding.password.apply {
            doAfterTextChanged {
                viewModel.loginDataChanged(binding.username.text.toString(), it.toString())
            }
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE &&
                    binding.loginBtn.isEnabled) {
                    binding.loginBtn.performClick()
                }
                false
            }
        }

        binding.loginBtn.setOnClickListener {
            viewModel.login(binding.username.text.toString(), binding.password.text.toString())
        }


        viewModel.loginBtnEnable.observe(this, {
            binding.loginBtn.isEnabled = it
        })

        viewModel.usernameError.observe(this, {
            if (!it.isNullOrBlank()) {
                MaterialSnackBar.make(binding.username, it).show()
            }
        })
        viewModel.passwordError.observe(this, Observer {
            if (!it.isNullOrBlank()) {
                MaterialSnackBar.make(binding.password, it).show()
            }
        })

        viewModel.proceedToApp.observe(this, EventObserver {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

    }

}