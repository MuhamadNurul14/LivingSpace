package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding

    private var minPrice = 500_000
    private var maxPrice = 2_000_000
    private var selectedType = "semua"
    private val selectedFacilities = mutableListOf<String>()

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

        // Back
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Reset
        binding.btnReset.setOnClickListener {
            resetFilters()
        }

        // Slider harga
        binding.sliderPrice.addOnChangeListener { _, value, _ ->
            maxPrice = value.toInt()
            binding.tvMaxPrice.text = "Rp ${maxPrice / 1000}K"
        }

        // Tipe kosan
        binding.btnTypePutra.setOnClickListener {
            selectedType = "putra"
            highlightTypeButton(binding.btnTypePutra)
        }

        binding.btnTypePutri.setOnClickListener {
            selectedType = "putri"
            highlightTypeButton(binding.btnTypePutri)
        }

        binding.btnTypeCampur.setOnClickListener {
            selectedType = "campur"
            highlightTypeButton(binding.btnTypeCampur)
        }

        // Apply
        binding.btnApply.setOnClickListener {
            applyFilters()
        }
    }

    private fun highlightTypeButton(selectedButton: androidx.appcompat.widget.AppCompatButton) {
        val buttons = listOf(
            binding.btnTypePutra,
            binding.btnTypePutri,
            binding.btnTypeCampur
        )

        buttons.forEach { button ->
            button.alpha = if (button == selectedButton) 1f else 0.5f
        }
    }

    private fun resetFilters() {
        minPrice = 500_000
        maxPrice = 2_000_000
        selectedType = "semua"
        selectedFacilities.clear()

        binding.tvMinPrice.text = "Rp ${minPrice / 1000}K"
        binding.tvMaxPrice.text = "Rp ${maxPrice / 1000}K"
        binding.sliderPrice.value = maxPrice.toFloat()

        // Reset visual button state
        binding.btnTypePutra.alpha = 1f
        binding.btnTypePutri.alpha = 1f
        binding.btnTypeCampur.alpha = 1f
    }

    private fun applyFilters() {
        val intent = Intent().apply {
            putExtra("MIN_PRICE", minPrice)
            putExtra("MAX_PRICE", maxPrice)
            putExtra("TYPE", selectedType)
            putStringArrayListExtra("FACILITIES", ArrayList(selectedFacilities))
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}
