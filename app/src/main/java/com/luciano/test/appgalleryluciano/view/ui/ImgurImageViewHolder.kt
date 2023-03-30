package com.luciano.test.appgalleryluciano.view.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luciano.test.appgalleryluciano.R
import com.luciano.test.appgalleryluciano.entity.ImgurImage

class ImgurImageViewHolder(private val v: View) :RecyclerView.ViewHolder(v) {
    fun bind(item: ImgurImage) {
        v.findViewById<TextView>(R.id.teste).text = item.description
    }
}