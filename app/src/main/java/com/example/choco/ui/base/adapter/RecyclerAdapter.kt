package com.example.choco.ui.base.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.choco.R
import com.example.choco.data.model.Product
import com.example.choco.extensions.inflate
import com.example.choco.ui.main.MainActivity
import com.example.choco.utils.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product.view.*

class RecyclerAdapter(var productList: MutableList<Product>,
                      private var context: Context) :
    ListAdapter<Product, RecyclerAdapter.ViewHolder>(UserDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_product))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    fun insertItems(items: List<Product>) {
        productList.clear()
        productList.addAll(items)
        notifyDataSetChanged()

    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(lastProduct: Product) {
            containerView.txt_name.text = lastProduct.name
            containerView.txt_price.text = lastProduct.price
            if (lastProduct.order) containerView.order_img.visibility = View.VISIBLE
            else containerView.order_img.visibility = View.GONE
            lastProduct.photo?.let { it1 ->
                ImageLoader.loadImageWithCircularCrop(
                    containerView.context,
                    it1,
                    containerView.item_image
                )
            }
            containerView.setOnClickListener(View.OnClickListener {
                if (context is MainActivity) {

                }
            })
        }
    }
}