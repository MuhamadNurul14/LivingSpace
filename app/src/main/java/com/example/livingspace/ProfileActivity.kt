package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val name = preferenceManager.getUserName()
        val email = preferenceManager.getUserEmail()

        binding.tvUserName.text = if (name.isNotEmpty()) name else "User"
        binding.tvUserEmail.text = if (email.isNotEmpty()) email else "user@example.com"

        val initial = if (name.isNotEmpty()) name.first().uppercase() else "U"
        binding.tvUserInitial.text = initial
    }

    private fun setupListeners() {
        binding.bottomNavigation.selectedItemId = R.id.nav_profile

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
                R.id.nav_history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }

        binding.layoutEditProfile.setOnClickListener {}
        binding.layoutSettings.setOnClickListener {}
        binding.layoutHelp.setOnClickListener {}
        binding.layoutAbout.setOnClickListener {}

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ -> logout() }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun logout() {
        preferenceManager.clearUserData()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_profile
    }
}
