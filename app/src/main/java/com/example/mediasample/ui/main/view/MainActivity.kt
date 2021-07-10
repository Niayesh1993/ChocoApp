package com.example.mediasample.ui.main.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.mediasample.R
import com.example.mediasample.data.model.Product
import com.example.mediasample.ui.base.adapter.RecyclerAdapter
import com.example.mediasample.ui.detail.view.DetailActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    private var listIndex = 0
    private lateinit var totalProducts: List<Product>
    private lateinit var temps: MutableList<Product>
    private var productlist: MutableList<Product> = mutableListOf(Product())

    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
    }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { RecyclerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    showMoreProducts()
                }
            }
        })

        subscribeObservers()
    }

    private fun subscribeObservers() {


        viewModel.productList.observe(this, Observer {
            totalProducts = it as List<Product>
            //create a new List with the subset of elements
            temps = totalProducts.slice(listIndex..listIndex + 9) as MutableList<Product>
            productlist.clear()
            productlist.addAll(temps)
            adapter.submitList(productlist)
            listIndex += 10
        })

        viewModel.loadProductListASynchronously(this)

    }

    private fun showMoreProducts() {

        temps.clear()
        //create a new List with the subset of elements
        temps = totalProducts.slice(listIndex + 1..listIndex + 10) as MutableList<Product>
        productlist.addAll(temps)
        adapter.notifyDataSetChanged()
        listIndex += 10

    }

    fun showDetail(product: Product) {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("product", product)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                var result = data?.getSerializableExtra("favorite")
                updateProducts(result as Product)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    private fun updateProducts(product: Product) {

        for (i in 0 until productlist!!.size) {
            Log.d("lets", productlist!!.get(i).title)

            if (productlist!!.get(i).title.equals(product.title)) {
                productlist!!.get(i).favorite = product.favorite
                adapter.notifyDataSetChanged()
                return
            }
        }


    }

}