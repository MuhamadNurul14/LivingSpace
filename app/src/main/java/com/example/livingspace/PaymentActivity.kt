package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private lateinit var preferenceManager: PreferenceManager
    private var kosan: Kosan? = null
    private var duration = 1
    private var totalPrice: Long = 0L // Diubah ke Long untuk menghindari mismatch
    private var bookingId: Long = -1L // Menangkap ID dari BookingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        // Tangkap data dari Intent
        kosan = intent.getParcelableExtra("KOSAN_DATA")
        duration = intent.getIntExtra("DURATION", 1)
        bookingId = intent.getLongExtra("BOOKING_ID", -1L) // ID Unik tertangkap di sini

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        kosan?.let { k ->
            binding.tvKosanName.text = k.name

            // Hitung total dengan tipe Long
            totalPrice = k.price.toLong() * duration
            binding.tvTotalAmount.text = "Rp ${totalPrice / 1000}K"

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

    private var selectedPaymentMethod = ""

    private fun processPayment() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnPayNow.isEnabled = false
        binding.btnPayNow.text = "Memproses..."

        // Simulasi proses bank selama 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            binding.progressBar.visibility = View.GONE

            // --- LOGIKA UPDATE STATUS DIMULAI ---
            if (bookingId != -1L) {
                // Update status di Repository menjadi SUCCESS
                BookingRepository.updateStatusToSuccess(bookingId)
            }
            // --- LOGIKA UPDATE STATUS SELESAI ---

            showSuccessDialog()
        }, 2000)
    }

    private fun showSuccessDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Pembayaran Berhasil")
            .setMessage("Pembayaran Anda telah berhasil diproses. Silakan cek riwayat untuk detail booking.")
            .setPositiveButton("Cek Riwayat") { _, _ ->
                navigateToHistory() // Langsung arahkan ke History untuk cek hasil otomatisnya
            }
            .setCancelable(false)
            .show()
    }

    private fun navigateToHistory() {
        // Pindah ke HistoryActivity dan hapus tumpukan activity agar tidak bisa "Back" ke bayar lagi
        val intent = Intent(this, HistoryActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}