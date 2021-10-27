package com.example.choco.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.choco.R
import com.example.choco.databinding.FragmentOrderBinding
import com.example.choco.ui.base.adapter.RecyclerAdapter
import com.example.choco.utils.observeUiState
import com.example.choco.utils.viewbinding.viewBindings

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OrderFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: FragmentOrderBinding by viewBindings()
    private val adapter = RecyclerAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState(viewModel.uiState)

        with(binding){

            recycler.adapter = adapter
            recycler.itemAnimator = DefaultItemAnimator()
        }
        with(viewModel){

            uiState.observe(viewLifecycleOwner, Observer {
                val uiModel = it ?: return@Observer

            })

            products.observe(viewLifecycleOwner, Observer {
                adapter.insertItems(it!!)
            })

            loadProducts()
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}