package com.example.livingspace

import android.view.LayoutInflater
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class OnboardingAdapter(
    private val onboardingItems: List<OnboardingItem>
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val textDescription = view.findViewById<TextView>(R.id.tvDescription)
        private val imageIllustration = view.findViewById<ImageView>(R.id.imageIllustration)


        fun bind(item: OnboardingItem) {  // ✅ tipe jelas
            textTitle.text = item.title
            textDescription.text = item.description

            Glide.with(imageIllustration.context)
                .load(item.imageUrl)
                .into(imageIllustration)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        return OnboardingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_onboarding, parent, false)
        )
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }

    override fun getItemCount() = onboardingItems.size
}
