package com.example.meteoapplication.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.meteoapplication.R
import timber.log.Timber


class BindingUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("meteoIcon")
        fun bindImageItemViewPersonnel(imgView: ImageView, icon: String?) {

            val url = "https://openweathermap.org/img/wn/$icon@2x.png"

            imgView.visibility = View.VISIBLE


            Glide.with(imgView.context)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_error_outline_24)
                        .error(R.drawable.ic_baseline_error_outline_24)
                )
                .into(imgView)
        }
    }
}