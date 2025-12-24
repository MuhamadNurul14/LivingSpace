package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livingspace.databinding.ActivityMainBinding

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
        val name = preferenceManager.getUserName()
        binding.tvWelcomeName.text = if (name.isNotEmpty()) "$name 👋" else "User 👋"

        // Tampilkan initial sebagai profil
        val initial = if (name.isNotEmpty()) name.first().uppercase() else "U"
        binding.tvProfileInitial.text = initial
    }

    private fun setupRecyclerViews() {
        // RECOMMENDED
        recommendedAdapter = KosanAdapter(
            kosanList = recommendedKosan,
            preferenceManager = preferenceManager,
            onItemClick = { navigateToDetail(it) }
        )

        binding.rvRecommended.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recommendedAdapter
        }

        // NEARBY
        nearbyAdapter = KosanAdapter(
            kosanList = nearbyKosan,
            preferenceManager = preferenceManager,
            onItemClick = { navigateToDetail(it) }
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
        binding.searchBar.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.tvProfileInitial.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.tvSeeAllRecommended.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding.tvSeeAllNearby.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.selectedItemId = R.id.nav_home

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
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

    private fun loadData() {
        val allKosan = Kosan.getKosanList()

        recommendedKosan.apply {
            clear()
            addAll(allKosan.take(2))
        }
        recommendedAdapter.notifyDataSetChanged()

        nearbyKosan.apply {
            clear()
            addAll(allKosan.drop(2).take(2))
        }
        nearbyAdapter.notifyDataSetChanged()
    }

    private fun navigateToDetail(kosan: Kosan) {
        val intent = Intent(this, DetailKosanActivity::class.java)
        intent.putExtra("KOSAN_ID", kosan.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_home

        // Refresh nama dan initial user
        setupViews()

        recommendedAdapter.notifyDataSetChanged()
        nearbyAdapter.notifyDataSetChanged()
    }
}
