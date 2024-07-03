package com.example.messenger.utils

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.messenger.R

fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
fun ImageView.loadImage(uri: String) {
    Glide.with(this)
        .load(uri)
        .placeholder(R.drawable.ic_user_image)
        .into(this)
}






