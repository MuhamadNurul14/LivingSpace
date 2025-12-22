package com.example.livingspace

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.livingspace.databinding.ActivityBookingBinding
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var preferenceManager: PreferenceManager
    private var kosan: Kosan? = null
    private var selectedDuration = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        // Ambil data dari Intent
        kosan = intent.getParcelableExtra("KOSAN_DATA")

        if (kosan == null) {
            Toast.makeText(this, "Data Kosan tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupViews()
        setupListeners()
        calculateTotal()
    }

    private fun setupViews() {
        kosan?.let { k ->
            binding.tvKosanName.text = k.name

            // Perbaikan: Pastikan ID ini ada di XML (tvLocation & tvPrice)
            // Jika merah, hapus 2 baris ini atau update XML
            // binding.tvLocation.text = k.location
            // binding.tvPrice.text = "Rp ${k.price / 1000}K/bulan"

            val localeID = Locale("id", "ID")
            val currentDate = SimpleDateFormat("dd MMM yyyy", localeID).format(Date())
            binding.etStartDate.setText(currentDate)

            val durations = arrayOf("1 bulan", "3 bulan", "6 bulan", "12 bulan")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, durations)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDuration.adapter = adapter

            // --- PERBAIKAN UTAMA DISINI ---
            // Menggunakan getUserName() sesuai file PreferenceManager Anda
            val name = preferenceManager.getUserName()
            val email = preferenceManager.getUserEmail()

            if (name != "User" && name.isNotEmpty()) binding.etFullName.setText(name)
            if (email.isNotEmpty()) binding.etEmail.setText(email)
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.spinnerDuration.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                selectedDuration = when(position) {
                    0 -> 1
                    1 -> 3
                    2 -> 6
                    3 -> 12
                    else -> 1
                }
                calculateTotal()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) { selectedDuration = 1 }
        }

        binding.btnProceedPayment.setOnClickListener {
            if (validateForm()) proceedToPayment()
        }
    }

    private fun calculateTotal() {
        kosan?.let { k ->
            val total = k.price * selectedDuration.toLong()
            binding.tvTotalPrice.text = "Rp ${total / 1000}K"

            // Format string manual agar tidak error unresolved reference
            binding.tvPriceBreakdown.text = "Rp ${k.price / 1000}K x $selectedDuration bulan\nTotal: Rp ${total / 1000}K"
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        if (binding.etFullName.text.toString().trim().isEmpty()) {
            binding.etFullName.error = "Wajib diisi"
            isValid = false
        }
        if (binding.etEmail.text.toString().trim().isEmpty()) {
            binding.etEmail.error = "Wajib diisi"
            isValid = false
        }
        if (binding.etPhone.text.toString().trim().isEmpty()) {
            binding.etPhone.error = "Wajib diisi"
            isValid = false
        }
        return isValid
    }

    private fun proceedToPayment() {
        kosan?.let { k ->

            val intent = Intent(this, PaymentActivity::class.java)

            intent.putExtra("KOSAN_DATA", k)
            intent.putExtra("DURATION", selectedDuration)
            intent.putExtra("FULL_NAME", binding.etFullName.text.toString())
            intent.putExtra("EMAIL", binding.etEmail.text.toString())
            intent.putExtra("PHONE", binding.etPhone.text.toString())
            intent.putExtra("START_DATE", binding.etStartDate.text.toString())
            intent.putExtra("NOTES", binding.etNotes.text.toString())

            startActivity(intent)
        }
    }
}