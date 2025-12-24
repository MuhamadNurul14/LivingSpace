package com.example.livingspace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.livingspace.databinding.ItemKosanBinding

class KosanAdapter(
    private val kosanList: MutableList<Kosan>,
    private val preferenceManager: PreferenceManager,
    private val onItemClick: (Kosan) -> Unit,
    private val isFavoritePage: Boolean = false,
    private val onFavoriteRemoved: ((Kosan) -> Unit)? = null
    ) : RecyclerView.Adapter<KosanAdapter.KosanViewHolder>() {

    inner class KosanViewHolder(
        private val binding: ItemKosanBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(kosan: Kosan) = with(binding) {

            // =====================
            // DATA
            // =====================
            tvKosanName.text = kosan.name
            tvLocation.text = kosan.location
            tvPrice.text = "Rp ${kosan.price / 1000}K / bln"
            tvRating.text = "⭐ ${kosan.rating}"
            tvDistance.text = kosan.distance

            Glide.with(root.context)
                .load(kosan.imageUrl)
                .centerCrop()
                .into(ivKosan)

            // =====================
            // FAVORITE ICON
            // =====================
            val isFavorite = preferenceManager.isFavorite(kosan.id)
            btnFavorite.setImageResource(
                if (isFavorite)
                    android.R.drawable.btn_star_big_on
                else
                    android.R.drawable.btn_star_big_off
            )

            // =====================
            // FAVORITE CLICK
            // =====================
            btnFavorite.setOnClickListener {

                val wasFavorite = preferenceManager.isFavorite(kosan.id)
                preferenceManager.toggleFavorite(kosan.id)

                if (isFavoritePage && wasFavorite) {
                    // ⭐ DI HALAMAN FAVORITE → HAPUS ITEM
                    onFavoriteRemoved?.invoke(kosan)
                } else {
                    // ⭐ DI HOME / SEARCH → HANYA UPDATE ICON
                    val pos = bindingAdapterPosition
                    if (pos != RecyclerView.NO_POSITION) {
                        notifyItemChanged(pos)
                    }
                }
            }



            // =====================
            // ITEM CLICK
            // =====================
            root.setOnClickListener {
                onItemClick(kosan)
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
