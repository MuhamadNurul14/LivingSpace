package com.example.livingspace.activities

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.livingspace.app.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        Toast.makeText(this, "Registrasi berhasil! Silakan login", Toast.LENGTH_SHORT).show()
        finish()
    }
}
