package com.example.livingspace

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvLoginLink.setOnClickListener {
            finish()
        }

        binding.btnSend.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()

            if (email.isEmpty()) {
                binding.tilEmail.error = "Email tidak boleh kosong"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilEmail.error = "Format email tidak valid"
            } else {
                binding.tilEmail.error = null
                sendResetInstructions(email)
            }
        }
    }

    private fun sendResetInstructions(email: String) {
        // Simulasi pengiriman email
        Toast.makeText(this, "Instruksi reset dikirim ke $email", Toast.LENGTH_LONG).show()

        // Menutup halaman setelah 2 detik dan kembali ke Login
        binding.btnSend.isEnabled = false
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            finish()
        }, 2000)
    }
}