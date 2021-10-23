package com.example.choco.ui.main

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.choco.databinding.ActivityMainBinding
import com.example.choco.utils.viewbinding.viewBindings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBindings()

}