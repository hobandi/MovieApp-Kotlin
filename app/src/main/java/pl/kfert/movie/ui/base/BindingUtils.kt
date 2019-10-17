package pl.kfert.movie.ui.base

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.kfert.movie.BuildConfig
import pl.kfert.movie.data.DataResult
import pl.kfert.movie.data.DataResult.Status.*
import pl.kfert.movie.data.model.Movie
import java.util.*

@BindingAdapter("loadSmallImage")
fun bindSmallImageFromUrl(view: ImageView, imageUrl: String?) {
    loadImage(view, BuildConfig.SMALL_IMAGE_URL+imageUrl)
}

@BindingAdapter("loadImage")
fun bindImageUrl(view: ImageView, imageUrl: String?) {
    loadImage(view, BuildConfig.SMALL_IMAGE_URL+imageUrl)
}

private fun loadImage(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("changeVisibility")
fun bindChangeVisibility(progressBar: ProgressBar, data : DataResult<Any>?) {

    Log.d("BindingAdapter",""+data?.status)
   when (data?.status) {
       SUCCESS -> progressBar.hide()
       LOADING -> progressBar.show()
       ERROR -> progressBar.hide()
    }
}

@BindingAdapter("selectFavorite")
fun bindSelectFavorite(view: View, isFavorite: Boolean?)
{
    Log.d("BindingAdapter","isFavorite $isFavorite")

    isFavorite?.let { view.isSelected = it }
}

