package com.example.mediasample.ui.detail.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mediasample.R
import com.example.mediasample.data.model.Product
import com.example.mediasample.ui.detail.viewmodel.DetailActivityViewModel
import com.example.mediasample.utils.ImageLoader
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity(), View.OnClickListener {

    lateinit var product: Product
    @Inject
    internal lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, factory).get(DetailActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = getIntent()
        subscribeObservers(intent)
    }

    private fun subscribeObservers(intent: Intent) {

        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(root_layout, text, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.product.observe(this, Observer {
            product = it
            loadData(product)
        })

        viewModel.fetchProduct(intent)
    }

    //initial activity UI
    private fun loadData(product: Product){
        title_text.text = product.title
        author_txt.text = product.author
        add_to_favorite_btn.setOnClickListener(this)
        remove_favorite_btn.setOnClickListener(this)
        product.imageURL?.let { it1 -> ImageLoader.loadImageWithCircularCrop(this, it1, product_image) }
        if (product.favorite) add_layout.visibility = View.GONE
        else remove_layout.visibility = View.GONE
    }

    override fun onClick(v: View?) {
      if (v!= null)
      {
          when(v.getId()){

              R.id.add_to_favorite_btn -> {
                  //add favorite state to product
                  product.favorite = true
                  viewModel.onSnackbarShown(product.favorite)
                  add_layout.visibility = View.GONE
                  remove_layout.visibility = View.VISIBLE
                  intent.putExtra("favorite", product)
                  setResult(RESULT_OK, intent)

              }

              R.id.remove_favorite_btn -> {
                  //remove favorite state from product
                  product.favorite = false
                  viewModel.onSnackbarShown(product.favorite)
                  add_layout.visibility = View.VISIBLE
                  remove_layout.visibility = View.GONE
                  intent.putExtra("favorite", product)
                  setResult(RESULT_OK, intent)
              }
          }
      }
    }
}