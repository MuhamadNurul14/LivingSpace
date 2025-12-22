package com.example.livingspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.livingspace.databinding.ItemKosanBinding

class KosanAdapter(
    private val kosanList: List<Kosan>,
    private val onItemClick: (Kosan) -> Unit,
    private val onFavoriteClick: (Kosan) -> Unit,
    private val isFavorite: (Int) -> Boolean = { false }
) : RecyclerView.Adapter<KosanAdapter.KosanViewHolder>() {

    inner class KosanViewHolder(
        private val binding: ItemKosanBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(kosan: Kosan) {
            binding.apply {
                tvKosanName.text = kosan.name
                tvLocation.text = kosan.location
                tvPrice.text = "Rp ${kosan.price / 1000}K/bln"
                tvRating.text = "⭐ ${kosan.rating}"
                tvDistance.text = kosan.distance

                Glide.with(root.context)
                    .load(kosan.imageUrl)
                    .centerCrop()
                    .into(ivKosan)

                btnFavorite.setImageResource(
                    if (isFavorite(kosan.id))
                        android.R.drawable.btn_star_big_on
                    else
                        android.R.drawable.btn_star_big_off
                )

                root.setOnClickListener { onItemClick(kosan) }
                btnFavorite.setOnClickListener {
                    onFavoriteClick(kosan)
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KosanViewHolder {
        val binding = ItemKosanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return KosanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KosanViewHolder, position: Int) {
        holder.bind(kosanList[position])
    }

    override fun getItemCount(): Int = kosanList.size
}
