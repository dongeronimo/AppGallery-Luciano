package com.luciano.test.appgalleryluciano.view.ui


import android.app.Activity
import android.media.ImageReader
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import com.luciano.test.appgalleryluciano.R
import com.luciano.test.appgalleryluciano.datasource.ImageSource
import com.luciano.test.appgalleryluciano.entity.ImgurImage
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ImgurImageViewHolder(private val v: View,
                           private val onClick:(ImgurImage)->Unit) :RecyclerView.ViewHolder(v) {
    val image = v.findViewById<ImageView>(R.id.image_item_img)
    val job = Job()
    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    val coroutineScope = CoroutineScope(coroutineContext)
    lateinit var disposable: Disposable
    fun bind(item: ImgurImage) {
        coroutineScope.launch {
            image.setOnClickListener {
                onClick(item)
            }
            val imageLoader = v.context.imageLoader
            disposable = imageLoader.enqueue(ImageRequest.Builder(v.context)
                .data(item.url)
                .target(image)
                .build())
            disposable.job.await()
        }
    }

    fun cleanup() {
        disposable.dispose()
        coroutineContext.cancel()
        job.cancel()

}