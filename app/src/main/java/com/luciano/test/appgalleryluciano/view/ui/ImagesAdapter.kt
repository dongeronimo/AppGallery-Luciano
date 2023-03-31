package com.luciano.test.appgalleryluciano.view.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.luciano.test.appgalleryluciano.R
import com.luciano.test.appgalleryluciano.datasource.ImageSource
import com.luciano.test.appgalleryluciano.entity.ImgurImage

private class ImagesDiffCallback: DiffUtil.ItemCallback<ImgurImage>() {
    override fun areItemsTheSame(oldItem: ImgurImage, newItem: ImgurImage) = oldItem == newItem

    override fun areContentsTheSame(oldItem: ImgurImage, newItem: ImgurImage) = oldItem == newItem
}
class ImagesAdapter(val onClick:(ImgurImage)->Unit): ListAdapter<ImgurImage, ImgurImageViewHolder>(ImagesDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgurImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImgurImageViewHolder(inflater.inflate(R.layout.image_item, parent, false), onClick)
    }

    override fun onBindViewHolder(holder: ImgurImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: ImgurImageViewHolder) {
        super.onViewRecycled(holder)
        holder.cleanup()
    }
}