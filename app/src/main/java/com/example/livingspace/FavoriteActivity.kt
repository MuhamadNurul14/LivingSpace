package com.example.livingspace


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livingspace.databinding.ActivityFavoriteBinding


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var adapter: KosanAdapter
    private val favoriteKosan = mutableListOf<Kosan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setupRecyclerView()
        setupListeners()
        loadFavorites()
    }

    private fun setupRecyclerView() {
        adapter = KosanAdapter(
            kosanList = favoriteKosan,
            onItemClick = { kosan -> navigateToDetail(kosan) },
            onFavoriteClick = { kosan -> toggleFavorite(kosan) },
            isFavorite = { id -> preferenceManager.isFavorite(id) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = this@FavoriteActivity.adapter
        }
    }

    private fun setupListeners() {
        binding.bottomNavigation.selectedItemId = R.id.nav_favorite
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_favorite -> true
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

    private fun loadFavorites() {
        favoriteKosan.clear()
        val favoriteIds = preferenceManager.getFavorites()
        val allKosan = Kosan.getKosanList()
        favoriteKosan.addAll(allKosan.filter { favoriteIds.contains(it.id) })
        adapter.notifyDataSetChanged()

        binding.tvFavoriteCount.text = "${favoriteKosan.size} kosan tersimpan"
    }

    private fun navigateToDetail(kosan: Kosan) {
        val intent = Intent(this, DetailKosanActivity::class.java)
        intent.putExtra("KOSAN_DATA", kosan)
        startActivity(intent)
    }

    private fun toggleFavorite(kosan: Kosan) {
        preferenceManager.removeFavorite(kosan.id)
        loadFavorites()
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
        binding.bottomNavigation.selectedItemId = R.id.nav_favorite
    }
}
