package com.livingspace.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.livingspace.app.R
import com.livingspace.app.databinding.ActivityProfileBinding
import com.livingspace.app.utils.PreferenceManager

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
        // Load user data
        val email = preferenceManager.getEmail()
        val name = preferenceManager.getName()

        binding.tvUserName.text = name.ifEmpty { "User" }
        binding.tvUserEmail.text = email.ifEmpty { "user@example.com" }

        // Get initial from name
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

        // Menu items
        binding.layoutEditProfile.setOnClickListener {
            // TODO: Navigate to edit profile
        }

        binding.layoutSettings.setOnClickListener {
            // TODO: Navigate to settings
        }

        binding.layoutHelp.setOnClickListener {
            // TODO: Navigate to help
        }

        binding.layoutAbout.setOnClickListener {
            // TODO: Navigate to about
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya") { _, _ ->
                logout()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun logout() {
        preferenceManager.clearUser()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigation.selectedItemId = R.id.nav_profile
    }
}
