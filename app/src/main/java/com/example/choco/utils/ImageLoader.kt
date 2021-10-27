package com.example.choco.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.choco.R

internal object ImageLoader {

    fun loadImageWithCircularCrop(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .error(R.drawable.image_icon)
            .apply(RequestOptions.circleCropTransform())
            .transition(withCrossFade())
            .into(imageView)
    }

}