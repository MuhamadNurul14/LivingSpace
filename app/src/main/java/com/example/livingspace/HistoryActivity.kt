package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.livingspace.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val historyList = mutableListOf<BookingHistory>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        // loadHistory() dihapus dari sini, dipindah ke onResume
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(historyList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = this@HistoryActivity.adapter
        }
    }

    private fun loadHistory() {
        // 1. Bersihkan list lama
        historyList.clear()

        // 2. Ambil data OTOMATIS dari Repository
        val currentData = BookingRepository.historyList
        historyList.addAll(currentData)

        // 3. Beritahu adapter ada data baru
        adapter.notifyDataSetChanged()

        // 4. Update teks jumlah riwayat
        binding.tvHistoryCount.text = getString(
            R.string.history_count,
            historyList.size
        )
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_history

        loadHistory()
    }

    private fun setupListeners() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_history -> true

                else -> false
            }
        }
    }
}