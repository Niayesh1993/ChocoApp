package com.example.mediasample.ui.base.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mediasample.R
import com.example.mediasample.data.model.Product
import com.example.mediasample.extensions.inflate
import com.example.mediasample.ui.main.view.MainActivity
import com.example.mediasample.utils.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product.view.*

class RecyclerAdapter(private val context: Context) :
    ListAdapter<Product, RecyclerAdapter.UserDateViewHolder>(UserDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDateViewHolder =
        UserDateViewHolder(parent.inflate(R.layout.item_product))

    override fun onBindViewHolder(holder: UserDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.title == newItem.title
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    inner class UserDateViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(product: Product) {
            containerView.txt_title.text = product.title
            containerView.txt_author.text = product.author
            if (product.favorite) containerView.favorite_img.visibility = View.VISIBLE
            else containerView.favorite_img.visibility = View.GONE
            product.imageURL?.let { it1 ->
                ImageLoader.loadImageWithCircularCrop(
                    containerView.context,
                    it1,
                    containerView.episode_item_image
                )
            }
            containerView.setOnClickListener(View.OnClickListener {
                if (context is MainActivity) {
                    (context as MainActivity).showDetail(product)
                }
            })
        }
    }
}