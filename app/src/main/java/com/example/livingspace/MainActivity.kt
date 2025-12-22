package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.livingspace.app.R
import com.livingspace.app.adapters.KosanAdapter
import com.livingspace.app.databinding.ActivityMainBinding
import com.livingspace.app.models.Kosan
import com.livingspace.app.models.KosanData
import com.livingspace.app.utils.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var recommendedAdapter: KosanAdapter
    private lateinit var nearbyAdapter: KosanAdapter

    private val recommendedKosan = mutableListOf<Kosan>()
    private val nearbyKosan = mutableListOf<Kosan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setupViews()
        setupRecyclerViews()
        setupListeners()
        loadData()
    }

    private fun setupViews() {
        // Set welcome message
        val userName = preferenceManager.getUserName()
        binding.tvWelcomeName.text = userName
    }

    private fun setupRecyclerViews() {
        // Recommended RecyclerView
        recommendedAdapter = KosanAdapter(
            kosanList = recommendedKosan,
            onItemClick = { kosan -> navigateToDetail(kosan) },
            onFavoriteClick = { kosan -> toggleFavorite(kosan) }
        )

        binding.rvRecommended.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recommendedAdapter
        }

        // Nearby RecyclerView (Grid)
        nearbyAdapter = KosanAdapter(
            kosanList = nearbyKosan,
            onItemClick = { kosan -> navigateToDetail(kosan) },
            onFavoriteClick = { kosan -> toggleFavorite(kosan) }
        )

        binding.rvNearby.apply {
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = nearbyAdapter
        }
    }

    private fun setupListeners() {
        binding.apply {
            // Search bar click
            searchBar.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }

            // Profile click
            ivProfile.setOnClickListener {
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }

            // See all recommended
            tvSeeAllRecommended.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }

            // See all nearby
            tvSeeAllNearby.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }

            // Bottom Navigation
            setupBottomNavigation()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_home

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already in home
                    true
                }
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadData() {
        val allKosan = KosanData.getKosanList()

        // Load recommended (first 2)
        recommendedKosan.clear()
        recommendedKosan.addAll(allKosan.take(2))
        recommendedAdapter.notifyDataSetChanged()

        // Load nearby (next 2)
        nearbyKosan.clear()
        nearbyKosan.addAll(allKosan.drop(2).take(2))
        nearbyAdapter.notifyDataSetChanged()
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
        // Refresh adapters
        recommendedAdapter.notifyDataSetChanged()
        nearbyAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        // Refresh bottom navigation selection
        binding.bottomNavigation.selectedItemId = R.id.nav_home
    }
}
