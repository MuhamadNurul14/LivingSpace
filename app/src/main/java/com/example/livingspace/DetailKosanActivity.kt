package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

        // Get kosan data from intent
        kosan = intent.getParcelableExtra("KOSAN_DATA")

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

            // Set type
            binding.tvType.text = when(k.type) {
                "putra" -> "Putra"
                "putri" -> "Putri"
                else -> "Campur"
            }

            // Description
            binding.tvDescription.text = "Kosan ${k.name} menawarkan kenyamanan dan lokasi strategis di ${k.location}. " +
                    "Dilengkapi dengan berbagai fasilitas yang memadai untuk memenuhi kebutuhan Anda."

            // Facilities
            val facilities = listOf("WiFi", "AC", "Kasur", "Lemari", "Kamar Mandi Dalam", "Parkir Motor")
            binding.tvFacilities.text = facilities.joinToString("\n") { "• $it" }

            // Check favorite status
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
                intent.putExtra("KOSAN_DATA", k)
                startActivity(intent)
            }
        }

        binding.btnContact.setOnClickListener {
            // TODO: Implement contact owner
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
        if (isFavorite) {
            binding.btnFavorite.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnFavorite.setImageResource(android.R.drawable.star_big_off)
        }
    }
}
