package com.example.choco.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.choco.databinding.ActivitySplashBinding
import com.example.choco.ui.login.LoginActivity
import com.example.choco.ui.main.MainActivity
import com.example.choco.utils.Constants
import com.example.choco.utils.SettingManager
import com.example.choco.utils.viewbinding.viewBindings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val binding: ActivitySplashBinding by viewBindings()
    val SPLASH_TIME_OUT = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.loading.isEnabled = true
        SettingManager.init(this)

        if (SettingManager.hasValue(Constants().ACCESS_TOKEN))
        {
            val intent = Intent(this, MainActivity::class.java)
            navigateToNextActivity(intent)
        }else
        {
            val intent = Intent(this, LoginActivity::class.java)
            navigateToNextActivity(intent)
        }


    }

    fun navigateToNextActivity(intent: Intent) {
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}