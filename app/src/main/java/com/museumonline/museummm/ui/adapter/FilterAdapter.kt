package com.museumonline.museummm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.museumonline.museummm.R
import com.museumonline.museummm.databinding.ItemFilterBinding
import com.museumonline.museummm.model.Direction
import com.museumonline.museummm.network.Contents
import com.squareup.picasso.Picasso
import io.michaelrocks.paranoid.Obfuscate

@Obfuscate
class FilterAdapter(
    var items: List<Direction>,
    var selectDirection: (direction: Direction) -> Unit
) :
    RecyclerView.Adapter<FilterAdapter.FilterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterVH {
        return FilterVH(
            ItemFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterVH, position: Int) {
        holder.binding.apply {

            tvName.text = items[position].nomi
            tvCategory.text = items[position].cat
            Picasso.get().load(Contents.BASE_URL.plus("/").plus(items[position].img)).placeholder(R.drawable.placeholder)
                .into(imgDirection)

            direction.setOnClickListener {
                selectDirection(items[position])
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class FilterVH(val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}