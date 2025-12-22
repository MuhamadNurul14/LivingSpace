package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livingspace.databinding.ActivityHistoryBinding


class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var adapter: HistoryAdapter
    private val historyList = mutableListOf<BookingHistory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setupRecyclerView()
        setupListeners()
        loadHistory()
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(historyList)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = this@HistoryActivity.adapter
        }
    }

    private fun setupListeners() {
        binding.bottomNavigation.selectedItemId = R.id.nav_history
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
                R.id.nav_favorite -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_history -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun loadHistory() {
        historyList.clear()

            historyList.addAll(
                listOf(
                    BookingHistory(
                        id = 1L,
                        kosanName = "Kosan Harmoni",
                        date = "10 Des 2024",
                        duration = "6 bulan",
                        price = 1500000,
                        status = BookingStatus.SUCCESS
                    ),
                    BookingHistory(
                        id = 2L,
                        kosanName = "Kosan Sejahtera",
                        date = "5 Des 2024",
                        duration = "3 bulan",
                        price = 900000,
                        status = BookingStatus.PENDING
                    ),
                    BookingHistory(
                        id = 3L,
                        kosanName = "Kosan Elite",
                        date = "1 Des 2024",
                        duration = "12 bulan",
                        price = 3600000,
                        status = BookingStatus.SUCCESS
                    )
                )
            )

        adapter.notifyDataSetChanged()

        binding.tvHistoryCount.text = getString(
            R.string.history_count,
            historyList.size
        )
    }


    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_history
    }
}
