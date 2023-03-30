package com.luciano.test.appgalleryluciano.view.ui

import android.media.ImageReader
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.luciano.test.appgalleryluciano.R
import com.luciano.test.appgalleryluciano.datasource.ImageSource
import com.luciano.test.appgalleryluciano.entity.ImgurImage
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ImgurImageViewHolder(private val v: View) :RecyclerView.ViewHolder(v) {
    val image = v.findViewById<ImageView>(R.id.image_item_img)
    val progress = v.findViewById<ProgressBar>(R.id.image_item_progress)
    val job = Job()
    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    val coroutineScope = CoroutineScope(coroutineContext)
    fun bind(item: ImgurImage, imageSource: ImageSource) {
        coroutineScope.launch {
            val imageLoader = v.context.imageLoader
            val disposable = imageLoader.enqueue(ImageRequest.Builder(v.context)
                .data(item.url)
                .target(image)
                .build())
            disposable.job.await()
            progress.visibility = View.GONE
        }
    }

    fun cleanup() {
        job.cancel()
    }
}