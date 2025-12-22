package com.livingspace.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.livingspace.app.R
import com.livingspace.app.databinding.ItemKosanBinding
import com.livingspace.app.models.Kosan

class KosanAdapter(
    private val kosanList: List<Kosan>,
    private val onItemClick: (Kosan) -> Unit,
    private val onFavoriteClick: (Kosan) -> Unit,
    private val isFavorite: (Int) -> Boolean = { false }
) : RecyclerView.Adapter<KosanAdapter.KosanViewHolder>() {

    inner class KosanViewHolder(private val binding: ItemKosanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(kosan: Kosan) {
            binding.apply {
                tvKosanName.text = kosan.name
                tvLocation.text = kosan.location
                tvPrice.text = "Rp ${kosan.price / 1000}K/bln"
                tvRating.text = "⭐ ${kosan.rating}"
                tvDistance.text = kosan.distance

                // Load image with Glide
                Glide.with(itemView.context)
                    .load(kosan.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.card_background)
                    .into(ivKosan)

                // Update favorite icon
                btnFavorite.setImageResource(
                    if (isFavorite(kosan.id)) {
                        android.R.drawable.btn_star_big_on
                    } else {
                        android.R.drawable.btn_star_big_off
                    }
                )

                // Click listeners
                root.setOnClickListener { onItemClick(kosan) }
                btnFavorite.setOnClickListener {
                    onFavoriteClick(kosan)
                    // Update icon immediately
                    btnFavorite.setImageResource(
                        if (isFavorite(kosan.id)) {
                            android.R.drawable.btn_star_big_on
                        } else {
                            android.R.drawable.btn_star_big_off
                        }
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KosanViewHolder {
        val binding = ItemKosanBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return KosanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KosanViewHolder, position: Int) {
        holder.bind(kosanList[position])
    }

    override fun getItemCount() = kosanList.size
}
