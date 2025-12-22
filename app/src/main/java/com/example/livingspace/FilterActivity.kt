package com.livingspace.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.livingspace.app.R
import com.livingspace.app.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding
    private var minPrice = 500000
    private var maxPrice = 3000000
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
        // Set initial price range
        binding.tvMinPrice.text = "Rp ${minPrice / 1000}K"
        binding.tvMaxPrice.text = "Rp ${maxPrice / 1000}K"

        // Setup facilities chips
        setupFacilitiesChips()
    }

    private fun setupFacilitiesChips() {
        val facilities = listOf("WiFi", "AC", "Kasur", "Lemari", "Kamar Mandi Dalam", "Parkir Motor", "Dapur", "Laundry")

        facilities.forEach { facility ->
            val chip = Chip(this).apply {
                text = facility
                isCheckable = true
                setChipBackgroundColorResource(R.color.purple_700)
                setTextColor(getColor(R.color.white))
                chipStrokeWidth = 2f
                setChipStrokeColorResource(R.color.purple_500)
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedFacilities.add(facility)
                    } else {
                        selectedFacilities.remove(facility)
                    }
                }
            }
            binding.chipGroupFacilities.addView(chip)
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnReset.setOnClickListener {
            resetFilters()
        }

        // Price range seekbar
        binding.seekBarPrice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                maxPrice = 500000 + (progress * 50000)
                binding.tvMaxPrice.text = "Rp ${maxPrice / 1000}K"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Type selection
        binding.radioGroupType.setOnCheckedChangeListener { group, checkedId ->
            selectedType = when(checkedId) {
                R.id.radioPutra -> "putra"
                R.id.radioPutri -> "putri"
                R.id.radioCampur -> "campur"
                else -> "semua"
            }
        }

        binding.btnApply.setOnClickListener {
            applyFilters()
        }
    }

    private fun resetFilters() {
        minPrice = 500000
        maxPrice = 3000000
        selectedType = "semua"
        selectedFacilities.clear()

        binding.tvMinPrice.text = "Rp ${minPrice / 1000}K"
        binding.tvMaxPrice.text = "Rp ${maxPrice / 1000}K"
        binding.seekBarPrice.progress = 50
        binding.radioGroupType.clearCheck()

        for (i in 0 until binding.chipGroupFacilities.childCount) {
            val chip = binding.chipGroupFacilities.getChildAt(i) as Chip
            chip.isChecked = false
        }
    }

    private fun applyFilters() {
        val intent = Intent()
        intent.putExtra("MIN_PRICE", minPrice)
        intent.putExtra("MAX_PRICE", maxPrice)
        intent.putExtra("TYPE", selectedType)
        intent.putStringArrayListExtra("FACILITIES", ArrayList(selectedFacilities))
        setResult(RESULT_OK, intent)
        finish()
    }
}
