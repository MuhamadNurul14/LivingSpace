package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var preferenceManager: PreferenceManager
    private var kosan: Kosan? = null
    private var duration = 1
    private var totalPrice = 0
    private var selectedPaymentMethod = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        // Get data from intent
        kosan = intent.getParcelableExtra("KOSAN_DATA")
        duration = intent.getIntExtra("DURATION", 1)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        kosan?.let { k ->
            binding.tvKosanName.text = k.name

            // --- BAGIAN INI DIHAPUS KARENA ID 'tvDuration' SUDAH TIDAK ADA DI XML ---
            // binding.tvDuration.text = "$duration bulan"
            // ------------------------------------------------------------------------

            totalPrice = k.price * duration
            binding.tvTotalAmount.text = "Rp ${totalPrice / 1000}K"

            // Show booking details
            val fullName = intent.getStringExtra("FULL_NAME") ?: ""
            val email = intent.getStringExtra("EMAIL") ?: ""
            val phone = intent.getStringExtra("PHONE") ?: ""
            val startDate = intent.getStringExtra("START_DATE") ?: ""

            binding.tvBookingDetails.text = buildString {
                append("Nama: $fullName\n")
                append("Email: $email\n")
                append("No. HP: $phone\n")
                append("Mulai: $startDate\n")
                append("Durasi: $duration bulan")
            }
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.radioGroupPayment.setOnCheckedChangeListener { _, checkedId ->
            val radio = findViewById<RadioButton>(checkedId)
            selectedPaymentMethod = radio.text.toString()
        }

        binding.btnPayNow.setOnClickListener {
            if (selectedPaymentMethod.isEmpty()) {
                Toast.makeText(this, "Pilih metode pembayaran terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            processPayment()
        }
    }

    private fun processPayment() {
        // Show loading
        binding.progressBar.visibility = View.VISIBLE
        binding.btnPayNow.isEnabled = false
        binding.btnPayNow.text = "Memproses..."

        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE
            showSuccessDialog()
        }, 2000)
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Pembayaran Berhasil")
            .setMessage("Pembayaran Anda telah berhasil diproses. Silakan cek email untuk detail booking.")
            .setPositiveButton("OK") { _, _ ->
                navigateToHome()
            }
            .setCancelable(false)
            .show()
    }

    private fun navigateToHome() {
        // Pastikan Anda memiliki MainActivity. Jika belum, buat filenya (lihat langkah 2 di bawah)
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}