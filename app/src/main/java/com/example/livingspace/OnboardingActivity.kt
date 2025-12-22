package com.example.livingspace.activities

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

import com.livingspace.app.R
import com.livingspace.app.LoginActivity
import com.livingspace.app.adapters.OnboardingAdapter
import com.livingspace.app.adapters.OnboardingItem

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnboardingAdapter
    private lateinit var indicatorsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // 1. Definisikan 3 Data Halaman Onboarding
        val items = listOf(
            OnboardingItem(
                title = "Cari Kosan Mudah",
                description = "Temukan ribuan pilihan kosan strategis di dekat kampus atau tempat kerjamu dengan mudah.",
                imageResId = R.drawable.ic_launcher_background // Pastikan gambar ini ada di drawable, atau ganti dengan gambar lain
            ),
            OnboardingItem(
                title = "Fasilitas Lengkap",
                description = "Filter pencarian berdasarkan fasilitas seperti WiFi, AC, Kamar Mandi Dalam, dan lainnya.",
                imageResId = R.drawable.ic_launcher_background
            ),
            OnboardingItem(
                title = "Pembayaran Aman",
                description = "Booking dan bayar kosan impianmu langsung dari aplikasi dengan metode pembayaran aman.",
                imageResId = R.drawable.ic_launcher_background
            )
        )

        onboardingAdapter = OnboardingAdapter(items)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = onboardingAdapter

        // Setup Indikator & Tombol
        indicatorsContainer = findViewById(R.id.indicators)
        setupIndicators(items.size)
        setCurrentIndicator(0)

        // Listener saat halaman digeser
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                val btnNext = findViewById<Button>(R.id.btnNext)
                // Ubah teks tombol jika sudah di halaman terakhir
                if (position == items.size - 1) {
                    btnNext.text = "Mulai"
                } else {
                    btnNext.text = "Lanjut"
                }
            }
        })

        // Setup Tombol Next
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            if (viewPager.currentItem + 1 < items.size) {
                viewPager.currentItem += 1
            } else {
                // Pindah ke halaman Login
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        // Setup Tombol Skip
        findViewById<Button>(R.id.btnSkip).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // Fungsi helper untuk membuat titik-titik indikator
    private fun setupIndicators(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive)
            )
            indicators[i]?.layoutParams = layoutParams
            indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_active)
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive)
                )
            }
        }
    }
}