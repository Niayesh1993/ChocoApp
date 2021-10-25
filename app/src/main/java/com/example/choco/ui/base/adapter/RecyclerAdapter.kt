package com.example.choco.ui.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.choco.R
import com.example.choco.data.model.Product
import com.example.choco.ui.widget.recyclerview.BaseRecyclerAdapter
import com.example.choco.ui.widget.recyclerview.BaseViewHolder
import com.example.choco.utils.ImageLoader
import kotlinx.android.synthetic.main.item_product.view.*

class RecyclerAdapter:
    BaseRecyclerAdapter<Product,
            RecyclerAdapter.ViewHolder,
            BaseViewHolder.OnItemClickListener<Product>>(){


    override fun viewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.item_product,
                parent,
                false
            ),
            this
        )
    }

    override fun onBindView(holder: ViewHolder?, position: Int) {
        holder?.bind(data[position])
    }


    class ViewHolder(itemView: View,
                     adapter: RecyclerAdapter,)
        : BaseViewHolder<Product>(itemView, adapter){

        private var mItemName: TextView = itemView.txt_name
        private var mItemDesc: TextView = itemView.txt_desc
        private var mItemPrice: TextView = itemView.txt_price


        override fun bind(t: Product) {

            mItemName.text = t.name
            mItemDesc.text = t.description
            mItemPrice.text = t.price
            t.photo?.let { it1 ->
                ImageLoader.loadImageWithCircularCrop(
                    itemView.context,
                    it1,
                    itemView.item_image
                )
            }
        }

    }

}