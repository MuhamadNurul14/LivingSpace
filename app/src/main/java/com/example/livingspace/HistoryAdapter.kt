package com.livingspace.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.livingspace.app.R
import com.livingspace.app.databinding.ItemHistoryBinding
import com.livingspace.app.models.BookingHistory
import com.livingspace.app.models.BookingStatus

class HistoryAdapter(
    private val historyList: List<BookingHistory>
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: BookingHistory) {
            binding.apply {
                tvKosanName.text = history.kosanName
                tvDate.text = history.date
                tvDuration.text = history.duration
                tvTotal.text = "Rp ${history.price / 1000}K"

                // Set status
                val statusText = when(history.status) {
                    BookingStatus.SUCCESS -> "Success"
                    BookingStatus.PENDING -> "Pending"
                    BookingStatus.CANCELLED -> "Cancelled"
                }
                tvStatus.text = statusText

                // Set status color
                val statusColor = when(history.status) {
                    BookingStatus.SUCCESS -> R.color.green_400
                    BookingStatus.PENDING -> R.color.yellow_400
                    BookingStatus.CANCELLED -> R.color.red_400
                }
                tvStatus.setTextColor(itemView.context.getColor(statusColor))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

    override fun getItemCount() = historyList.size
}
