package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livingspace.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var adapter: KosanAdapter

    // All kosan data
    private val allKosan = Kosan.getKosanList()
    private val filteredKosan = mutableListOf<Kosan>()

    // FILTER STATE
    private var selectedType = "Semua"
    private var minPrice = 0
    private var maxPrice = Int.MAX_VALUE
    private var minRating = 0f
    private var selectedFacilities = listOf<String>()

    // Result launcher for FilterActivity
    private val filterLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->

                    // PRICE
                    minPrice = intent.getIntExtra("MIN_PRICE", 0)
                    maxPrice = intent.getIntExtra("MAX_PRICE", Int.MAX_VALUE)

                    // TYPE
                    selectedType = intent.getStringExtra("TYPE") ?: "Semua"

                    // RATING
                    minRating = intent.getFloatExtra("MIN_RATING", 0f)

                    // FACILITIES
                    selectedFacilities =
                        intent.getStringArrayListExtra("FACILITIES") ?: emptyList()

                    syncChipState()
                    filterKosan(binding.etSearch.text.toString())
                }
            }
        }

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

        // Open FilterActivity
        binding.btnFilter.setOnClickListener {
            filterLauncher.launch(Intent(this, FilterActivity::class.java))
        }

        // Search realtime
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterKosan(s.toString())
            }
        })

        // Type chips
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

        val result = allKosan.filter { kosan ->

            val searchMatch =
                kosan.name.contains(query, true) ||
                        kosan.location.contains(query, true)

            val typeMatch =
                selectedType.equals("Semua", true) ||
                        kosan.type.equals(selectedType, true)

            val priceMatch =
                kosan.price in minPrice..maxPrice

            val ratingMatch =
                kosan.rating >= minRating

            val facilityMatch = if (selectedFacilities.isEmpty()) {
                // Jika user belum memilih fasilitas apapun → tampilkan semua
                true
            } else {
                // Jika user memilih 1 atau lebih fasilitas → tampilkan kosan yang punya semua fasilitas tersebut
                val kosanFacilitiesNormalized = kosan.facilities.map { it.trim().lowercase() }.toSet()
                selectedFacilities.map { it.trim().lowercase() }.all { it in kosanFacilitiesNormalized }
            }




            searchMatch &&
                    typeMatch &&
                    priceMatch &&
                    ratingMatch &&
                    facilityMatch
        }

        filteredKosan.addAll(result)
        adapter.notifyDataSetChanged()
        updateResultCount()
    }

    private fun filterByType(type: String) {
        selectedType = type
        syncChipState()
        filterKosan(binding.etSearch.text.toString())
    }

    private fun syncChipState() {
        binding.chipAll.isChecked = selectedType == "Semua"
        binding.chipPutra.isChecked = selectedType == "Putra"
        binding.chipPutri.isChecked = selectedType == "Putri"
        binding.chipCampur.isChecked = selectedType == "Campur"
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
