package com.museumonline.museummm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.museumonline.museummm.R
import com.museumonline.museummm.databinding.ItemImageArtBinding
import com.museumonline.museummm.model.Art
import com.museumonline.museummm.network.Contents
import com.squareup.picasso.Picasso
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class ArtAdapter(
    private var items: List<Art>,
    private var screenWidth: Int,
    val onCLick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<ArtAdapter.ImageVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        return ImageVH(
            ItemImageArtBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageVH, position: Int) {

        val imgUrl = items[position].img!!.split("$")[2]
        holder.binding.rlContainer.apply {
            val layoutParams = LinearLayout.LayoutParams(screenWidth / 2 - 4, WRAP_CONTENT)
            setLayoutParams(layoutParams)
        }

        Picasso.get().load(Contents.BASE_URL.plus("/images/asar/").plus(imgUrl)).placeholder(R.drawable.placeholder)
            .into(holder.binding.imgArt)

        holder.binding.imgArt.setOnClickListener {
            onCLick(position)
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ImageVH(var binding: ItemImageArtBinding) : RecyclerView.ViewHolder(binding.root) {
    }


}