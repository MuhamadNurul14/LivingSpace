package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (validateInput(email, password)) {
                    performLogin(email, password)
                }
            }

            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            btnTogglePassword.setOnClickListener {
                val selection = etPassword.selectionEnd
                val isHidden =
                    etPassword.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)

                etPassword.inputType =
                    if (isHidden)
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                etPassword.setSelection(selection)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email tidak boleh kosong"
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Format email tidak valid"
            return false
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password tidak boleh kosong"
            return false
        }

        binding.tilEmail.error = null
        binding.tilPassword.error = null
        return true
    }

    private fun performLogin(email: String, password: String) {

        // Ambil data hasil REGISTER
        val savedEmail = preferenceManager.getUserEmail()
        val savedPassword = preferenceManager.getUserPhone() // sementara pakai phone / ganti jika ada password

        // ⚠️ Jika belum daftar
        if (savedEmail.isEmpty()) {
            Toast.makeText(this, "Silakan daftar terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi login
        if (email == savedEmail && password.isNotEmpty()) {

            preferenceManager.setUserLoggedIn(true)

            Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        } else {
            Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
        }
    }
}
