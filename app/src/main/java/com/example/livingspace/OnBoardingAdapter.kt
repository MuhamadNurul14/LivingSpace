package com.example.livingspace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
// Pastikan baris ini ada dan tidak merah
import com.example.livingspace.R

class OnboardingAdapter(private val onboardingItems: List<OnboardingAdapter.OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    // --- Data Class (Nested) ---
    // Posisi di dalam class OnboardingAdapter agar menjadi satu kesatuan (Icon C)
    data class OnboardingItem(
        val title: String,
        val description: String,
        val imageResId: Int
    )

    // --- ViewHolder ---
    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Menggunakan binding manual (findViewById) sesuai layout XML Anda
        private val textTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val textDescription = view.findViewById<TextView>(R.id.tvDescription)
        private val imageIllustration = view.findViewById<View>(R.id.ivIllustration)

        fun bind(item: OnboardingItem) {
            textTitle.text = item.title
            textDescription.text = item.description
            imageIllustration.setBackgroundResource(item.imageResId)
        }
    }

    // --- Methods Override ---
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        // Memanggil layout 'item_onboarding' yang sudah Anda buat
        return OnboardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_onboarding,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }
}