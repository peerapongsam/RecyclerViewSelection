package io.github.peerpongsam.example.recyclerviewselection

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image.*

class ImageViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindValue(image: Image) {
        Glide.with(imageView)
            .load(image.url)
            .into(imageView)
    }
}
