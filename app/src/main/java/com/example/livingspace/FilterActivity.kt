package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityFilterBinding
import com.google.android.material.button.MaterialButton

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    private var minPrice = 500_000
    private var maxPrice = 2_000_000
    private var selectedType = "Semua"
    private var minRating = 0f

    // 🔥 FASILITAS (pakai Set biar tidak dobel)
    private val selectedFacilities = mutableSetOf<String>() // hanya deklarasi satu kali

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.tvMinPrice.text = "Rp ${minPrice / 1000}K"
        binding.tvMaxPrice.text = "Rp ${maxPrice / 1000}K"
        binding.sliderPrice.value = maxPrice.toFloat()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }
        binding.btnReset.setOnClickListener { resetFilters() }

        // 💰 Harga
        binding.sliderPrice.addOnChangeListener { _, value, _ ->
            maxPrice = value.toInt()
            binding.tvMaxPrice.text = "Rp ${maxPrice / 1000}K"
        }

        // ⭐ RATING
        binding.btnRate4.setOnClickListener { minRating = 4f; highlightRatingButton(binding.btnRate4) }
        binding.btnRate45.setOnClickListener { minRating = 4.5f; highlightRatingButton(binding.btnRate45) }
        binding.btnRate48.setOnClickListener { minRating = 4.8f; highlightRatingButton(binding.btnRate48) }
        binding.btnRate5.setOnClickListener { minRating = 5f; highlightRatingButton(binding.btnRate5) }

        // 🏠 Tipe kos
        binding.btnTypePutra.setOnClickListener { selectedType = "Putra"; highlightTypeButton(binding.btnTypePutra) }
        binding.btnTypePutri.setOnClickListener { selectedType = "Putri"; highlightTypeButton(binding.btnTypePutri) }
        binding.btnTypeCampur.setOnClickListener { selectedType = "Campur"; highlightTypeButton(binding.btnTypeCampur) }

        // 🧩 FASILITAS (TOGGLE)
        setupFacility(binding.btnWifi, "WiFi")
        setupFacility(binding.btnAC, "AC")
        setupFacility(binding.btnKasur, "Kasur")
        setupFacility(binding.btnLemari, "Lemari")
        setupFacility(binding.btnKMd, "Kamar Mandi Dalam")
        setupFacility(binding.btnDapur, "Dapur")
        setupFacility(binding.btnParkir, "Parkir")
        setupFacility(binding.btnCCTV, "CCTV")

        // ✅ Apply
        binding.btnApply.setOnClickListener { applyFilters() }
    }

    private fun setupFacility(button: MaterialButton, value: String) {
        // Set default OFF
        button.alpha = 0.5f
        button.setBackgroundColor(resources.getColor(android.R.color.transparent))
        button.setTextColor(resources.getColor(android.R.color.white))

        button.setOnClickListener {
            if (selectedFacilities.contains(value)) {
                selectedFacilities.remove(value)
                button.alpha = 0.5f
                button.setBackgroundColor(resources.getColor(android.R.color.transparent))
                button.setTextColor(resources.getColor(android.R.color.white))
            } else {
                selectedFacilities.add(value)
                button.alpha = 1f
                button.setBackgroundColor(resources.getColor(android.R.color.white))
                button.setTextColor(resources.getColor(android.R.color.black))
            }
        }
    }

    private fun highlightRatingButton(selected: MaterialButton) {
        listOf(binding.btnRate4, binding.btnRate45, binding.btnRate48, binding.btnRate5).forEach {
            if (it == selected) {
                it.alpha = 1f
                it.setBackgroundColor(resources.getColor(android.R.color.white))
                it.setTextColor(resources.getColor(android.R.color.black))
            } else {
                it.alpha = 0.5f
                it.setBackgroundColor(resources.getColor(android.R.color.transparent))
                it.setTextColor(resources.getColor(android.R.color.white))
            }
        }
    }

    private fun highlightTypeButton(selected: MaterialButton) {
        listOf(binding.btnTypePutra, binding.btnTypePutri, binding.btnTypeCampur).forEach {
            if (it == selected) {
                it.alpha = 1f
                it.setBackgroundColor(resources.getColor(android.R.color.white))
                it.setTextColor(resources.getColor(android.R.color.black))
            } else {
                it.alpha = 0.5f
                it.setBackgroundColor(resources.getColor(android.R.color.transparent))
                it.setTextColor(resources.getColor(android.R.color.white))
            }
        }
    }

    private fun resetFilters() {
        minPrice = 500_000
        maxPrice = 2_000_000
        selectedType = "Semua"
        minRating = 0f
        selectedFacilities.clear()

        binding.sliderPrice.value = maxPrice.toFloat()

        listOf(
            binding.btnRate4, binding.btnRate45, binding.btnRate48, binding.btnRate5,
            binding.btnTypePutra, binding.btnTypePutri, binding.btnTypeCampur,
            binding.btnWifi, binding.btnAC, binding.btnKasur, binding.btnLemari,
            binding.btnKMd, binding.btnDapur, binding.btnParkir, binding.btnCCTV
        ).forEach {
            it.alpha = 0.5f
            it.setBackgroundColor(resources.getColor(android.R.color.transparent))
            it.setTextColor(resources.getColor(android.R.color.white))
        }
    }

    private fun applyFilters() {
        val intent = Intent().apply {
            putExtra("MIN_PRICE", minPrice)
            putExtra("MAX_PRICE", maxPrice)
            putExtra("TYPE", selectedType)
            putExtra("MIN_RATING", minRating)
            putStringArrayListExtra("FACILITIES", ArrayList(selectedFacilities))
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
