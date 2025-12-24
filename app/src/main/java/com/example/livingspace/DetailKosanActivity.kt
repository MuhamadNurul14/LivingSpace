package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.livingspace.databinding.ActivityDetailKosanBinding

class DetailKosanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKosanBinding
    private lateinit var preferenceManager: PreferenceManager
    private var kosan: Kosan? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKosanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        // Ambil ID kosan dari intent
        val kosanId = intent.getIntExtra("KOSAN_ID", -1)

        // Cari kosan dari companion object
        kosan = Kosan.getKosanList().find { it.id == kosanId }

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        kosan?.let { k ->
            binding.tvKosanName.text = k.name
            binding.tvLocation.text = k.location
            binding.tvPrice.text = "Rp ${k.price / 1000}K/bulan"
            binding.tvRating.text = k.rating.toString()
            binding.tvReviews.text = "(${k.reviews} ulasan)"
            binding.tvType.text = k.type

            binding.tvDescription.text = k.description
            binding.tvFacilities.text = k.facilities.joinToString("\n") { "• $it" }

            // Load image
            Glide.with(this)
                .load(k.imageUrl)
                .into(binding.imgKosan)

            // Favorite
            isFavorite = preferenceManager.isFavorite(k.id)
            updateFavoriteIcon()
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnFavorite.setOnClickListener {
            toggleFavorite()
        }

        binding.btnBookNow.setOnClickListener {
            kosan?.let { k ->
                val intent = Intent(this, BookingActivity::class.java)
                intent.putExtra("KOSAN_ID", k.id)
                startActivity(intent)
            }
        }

        binding.btnContact.setOnClickListener {
            // Bisa lanjut WhatsApp / Dial
        }
    }

    private fun toggleFavorite() {
        kosan?.let { k ->
            if (isFavorite) {
                preferenceManager.removeFavorite(k.id)
                isFavorite = false
            } else {
                preferenceManager.addFavorite(k.id)
                isFavorite = true
            }
            updateFavoriteIcon()
        }
    }

    private fun updateFavoriteIcon() {
        binding.btnFavorite.setImageResource(
            if (isFavorite)
                android.R.drawable.star_big_on
            else
                android.R.drawable.star_big_off
        )
    }
}
