package com.example.livingspace.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livingspace.R
import com.livingspace.app.adapters.KosanAdapter
import com.livingspace.app.databinding.ActivitySearchBinding
import com.livingspace.app.models.Kosan
import com.livingspace.app.models.KosanData
import com.livingspace.app.utils.PreferenceManager

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var adapter: KosanAdapter

    private val allKosan = KosanData.getKosanList()
    private val filteredKosan = mutableListOf<Kosan>()
    private var selectedType = "Semua"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setupRecyclerView()
        setupListeners()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = KosanAdapter(
            kosanList = filteredKosan,
            onItemClick = { kosan -> navigateToDetail(kosan) },
            onFavoriteClick = { kosan -> toggleFavorite(kosan) },
            isFavorite = { id -> preferenceManager.isFavorite(id) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = this@SearchActivity.adapter
        }
    }

    private fun setupListeners() {
        binding.apply {
            btnBack.setOnClickListener { finish() }

            btnFilter.setOnClickListener {
                startActivity(Intent(this@SearchActivity, FilterActivity::class.java))
            }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    filterKosan(s.toString())
                }
            })

            // Category chips
            chipAll.setOnClickListener { filterByType("Semua") }
            chipPutra.setOnClickListener { filterByType("Putra") }
            chipPutri.setOnClickListener { filterByType("Putri") }
            chipCampur.setOnClickListener { filterByType("Campur") }

            // Bottom Navigation
            setupBottomNavigation()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_search
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_search -> true
                R.id.nav_favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadData() {
        filteredKosan.clear()
        filteredKosan.addAll(allKosan)
        adapter.notifyDataSetChanged()
        updateResultCount()
    }

    private fun filterKosan(query: String) {
        filteredKosan.clear()

        val filtered = allKosan.filter {
            (it.name.contains(query, ignoreCase = true) ||
                    it.location.contains(query, ignoreCase = true)) &&
                    (selectedType == "Semua" || it.type == selectedType)
        }

        filteredKosan.addAll(filtered)
        adapter.notifyDataSetChanged()
        updateResultCount()
    }

    private fun filterByType(type: String) {
        selectedType = type
        filterKosan(binding.etSearch.text.toString())

        // Update chip selection
        binding.apply {
            chipAll.isChecked = type == "Semua"
            chipPutra.isChecked = type == "Putra"
            chipPutri.isChecked = type == "Putri"
            chipCampur.isChecked = type == "Campur"
        }
    }

    private fun updateResultCount() {
        binding.tvResultCount.text = "Ditemukan ${filteredKosan.size} kosan"
    }

    private fun navigateToDetail(kosan: Kosan) {
        val intent = Intent(this, DetailKosanActivity::class.java)
        intent.putExtra("KOSAN_DATA", kosan)
        startActivity(intent)
    }

    private fun toggleFavorite(kosan: Kosan) {
        if (preferenceManager.isFavorite(kosan.id)) {
            preferenceManager.removeFavorite(kosan.id)
        } else {
            preferenceManager.addFavorite(kosan.id)
        }
        adapter.notifyDataSetChanged()
    }
}
