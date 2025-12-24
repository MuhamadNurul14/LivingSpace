package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        preferenceManager = PreferenceManager(this)

        // Navigate after 2.5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, 2500)
    }

    private fun navigateToNextScreen() {
        val intent = when {
            !preferenceManager.isOnboardingCompleted() -> {
                Intent(this, OnboardingActivity::class.java)
            }
            preferenceManager.isUserLoggedIn() -> {
                Intent(this, MainActivity::class.java)
            }
            else -> {
                Intent(this, LoginActivity::class.java)
            }
        }

        startActivity(intent)
        finish()
    }

}
