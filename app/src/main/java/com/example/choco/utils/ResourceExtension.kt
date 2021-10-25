package com.example.choco.utils

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.choco.ChocoApplication

fun Int.stringRes(vararg objects: Any?): String {
    return ChocoApplication.getContext().resources.getString(this, *objects)
}

fun Int.stringRes(): String {
    return ChocoApplication.getContext().resources.getString(this)
}

fun Int.colorRes(): Int {
    return ContextCompat.getColor(ChocoApplication.getContext(), this)
}

fun Int.drawableRes(): Drawable {
    return ContextCompat.getDrawable(ChocoApplication.getContext(), this)!!
}

fun Int.dimenRes(): Int {
    return ChocoApplication.getContext().resources.getDimensionPixelOffset(this)
}

fun Int.toPx(): Int {
    return ((this * ChocoApplication.getContext().resources.displayMetrics.density + 0.5f).toInt())
}