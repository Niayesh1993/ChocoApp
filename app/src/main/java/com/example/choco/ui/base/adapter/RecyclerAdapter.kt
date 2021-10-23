package com.example.choco.ui.base.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.choco.R
import com.example.choco.data.model.LastProduct
import com.example.choco.extensions.inflate
import com.example.choco.ui.main.MainActivity
import com.example.choco.utils.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product.view.*

class RecyclerAdapter(private val context: Context) :
    ListAdapter<LastProduct, RecyclerAdapter.UserDateViewHolder>(UserDataAdapterListDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDateViewHolder =
        UserDateViewHolder(parent.inflate(R.layout.item_product))

    override fun onBindViewHolder(holder: UserDateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class UserDataAdapterListDiff : DiffUtil.ItemCallback<LastProduct>() {

        override fun areItemsTheSame(oldItem: LastProduct, newItem: LastProduct): Boolean {
            return oldItem.title == newItem.title
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: LastProduct, newItem: LastProduct): Boolean {
            return oldItem == newItem
        }

    }

    inner class UserDateViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(lastProduct: LastProduct) {
            containerView.txt_title.text = lastProduct.title
            containerView.txt_author.text = lastProduct.author
            if (lastProduct.favorite) containerView.favorite_img.visibility = View.VISIBLE
            else containerView.favorite_img.visibility = View.GONE
            lastProduct.imageURL?.let { it1 ->
                ImageLoader.loadImageWithCircularCrop(
                    containerView.context,
                    it1,
                    containerView.episode_item_image
                )
            }
            containerView.setOnClickListener(View.OnClickListener {
                if (context is MainActivity) {

                }
            })
        }
    }
}