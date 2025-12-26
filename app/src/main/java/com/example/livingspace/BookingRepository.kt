package com.example.livingspace

object BookingRepository {
    val historyList = mutableListOf<BookingHistory>()

    fun addBooking(booking: BookingHistory) {
        historyList.add(0, booking)
    }

    // TAMBAHKAN FUNGSI INI:
    fun updateStatusToSuccess(bookingId: Long) {
        val index = historyList.indexOfFirst { it.id == bookingId }
        if (index != -1) {
            // Kita buat salinan data dengan status baru
            val updatedBooking = historyList[index].copy(status = BookingStatus.SUCCESS)
            historyList[index] = updatedBooking
        }
    }
}