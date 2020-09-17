package com.example.movieshop.ui.common

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movieshop.R

fun ImageView.loadFromUrl(url: String) {
    if (url.isNotEmpty()) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_movie)
            .error(R.drawable.ic_movie)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }
}

fun ImageView.loadFromDrawable(@DrawableRes res: Int) {
    Glide.with(this)
        .load(res)
        .into(this)
}

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

fun Fragment.setNavigationResult(result: String, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}