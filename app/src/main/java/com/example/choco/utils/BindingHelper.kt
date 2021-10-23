package com.example.choco.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object BindingHelper {

    @JvmStatic
    fun drawable(context: Context?, drawableResource: Int): Drawable? {
        return if (drawableResource == 0) null else context?.let { ContextCompat.getDrawable(it, drawableResource) }
    }

    @JvmStatic
    fun string(context: Context, stringResource: Int): String {
        return if (stringResource == 0) "" else context.resources.getString(stringResource)
    }
}