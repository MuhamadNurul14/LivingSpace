package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livingspace.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var adapter: KosanAdapter

    private val allKosan = Kosan.getKosanList()
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
            kosanList = filteredKosan, // ✅ GANTI DI SINI
            preferenceManager = preferenceManager,
            onItemClick = { navigateToDetail(it) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = this@SearchActivity.adapter
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterKosan(s.toString())
            }
        })

        // Chips
        binding.chipAll.setOnClickListener { filterByType("Semua") }
        binding.chipPutra.setOnClickListener { filterByType("Putra") }
        binding.chipPutri.setOnClickListener { filterByType("Putri") }
        binding.chipCampur.setOnClickListener { filterByType("Campur") }

        setupBottomNavigation()
    }

    private fun loadData() {
        filteredKosan.clear()
        filteredKosan.addAll(allKosan)
        adapter.notifyDataSetChanged()
        updateResultCount()
    }

    private fun filterKosan(query: String) {
        filteredKosan.clear()

        val result = allKosan.filter {
            (it.name.contains(query, true) ||
                    it.location.contains(query, true)) &&
                    (selectedType == "Semua" || it.type == selectedType)
        }

        filteredKosan.addAll(result)
        adapter.notifyDataSetChanged()
        updateResultCount()
    }

    private fun filterByType(type: String) {
        selectedType = type
        filterKosan(binding.etSearch.text.toString())

        binding.chipAll.isChecked = type == "Semua"
        binding.chipPutra.isChecked = type == "Putra"
        binding.chipPutri.isChecked = type == "Putri"
        binding.chipCampur.isChecked = type == "Campur"
    }

    private fun updateResultCount() {
        binding.tvResultCount.text = "Ditemukan ${filteredKosan.size} kosan"
    }

    private fun navigateToDetail(kosan: Kosan) {
        val intent = Intent(this, DetailKosanActivity::class.java)
        intent.putExtra("KOSAN_ID", kosan.id)
        startActivity(intent)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_search
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_search -> true
                R.id.nav_favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    true
                }
                R.id.nav_history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
