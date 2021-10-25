package com.example.choco.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.choco.databinding.ActivityMainBinding
import com.example.choco.ui.base.adapter.RecyclerAdapter
import com.example.choco.utils.observeUiState
import com.example.choco.utils.viewbinding.viewBindings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBindings()
    private val adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUiState(viewModel.uiState)

        with(binding){

            recycler.adapter = adapter
            recycler.itemAnimator = DefaultItemAnimator()
        }
        with(viewModel){

            uiState.observe(this@MainActivity, Observer {
                val uiModel = it ?: return@Observer

            })

            products.observe(this@MainActivity, Observer {
                adapter.insertItems(it!!)
            })

            loadProducts()
        }
    }

}