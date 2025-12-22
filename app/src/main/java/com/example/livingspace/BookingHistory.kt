package com.example.livingspace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingHistory(
    val id: Long,
    val kosanName: String,
    val date: String,
    val duration: String,
    val price: Int,
    val status: BookingStatus
) : Parcelable
