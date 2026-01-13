package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {

            btnRegister.setOnClickListener {
                if (validateInput()) {
                    performRegister()
                }
            }

            tvLogin.setOnClickListener {
                finish()
            }

            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (name.isEmpty()) {
            binding.tilName.error = "Nama tidak boleh kosong"
            return false
        }
        binding.tilName.error = null

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Email tidak valid"
            return false
        }
        binding.tilEmail.error = null

        if (phone.isEmpty()) {
            binding.tilPhone.error = "Nomor telepon tidak boleh kosong"
            return false
        }
        binding.tilPhone.error = null

        if (password.length < 6) {
            binding.tilPassword.error = "Password minimal 6 karakter"
            return false
        }
        binding.tilPassword.error = null

        if (password != confirmPassword) {
            binding.tilConfirmPassword.error = "Password tidak sama"
            return false
        }
        binding.tilConfirmPassword.error = null

        return true
    }

    private fun performRegister() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString()

        preferenceManager.setUserData(
            name = name,
            email = email,
            phone = phone,
            password = password
        )

        // Auto login setelah register
        preferenceManager.setUserLoggedIn(true)

        Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
