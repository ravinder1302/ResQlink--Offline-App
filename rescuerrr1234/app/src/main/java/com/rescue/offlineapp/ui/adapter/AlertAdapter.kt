package com.rescue.offlineapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rescue.offlineapp.R
import com.rescue.offlineapp.data.AlertEntity
import com.rescue.offlineapp.data.AlertMessage
import com.rescue.offlineapp.databinding.ItemAlertBinding
import java.text.SimpleDateFormat
import java.util.*

class AlertAdapter(
    private val onAcceptClick: (AlertEntity) -> Unit
) : ListAdapter<AlertEntity, AlertAdapter.AlertViewHolder>(AlertDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        val binding = ItemAlertBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlertViewHolder(binding, onAcceptClick)
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AlertViewHolder(
        private val binding: ItemAlertBinding,
        private val onAcceptClick: (AlertEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())

        fun bind(alert: AlertEntity) {
            binding.apply {
                tvNeed.text = alert.need
                tvPriority.text = alert.priority.name
                tvMessage.text = alert.message ?: "No additional message"
                tvLocation.text = "${alert.latitude}, ${alert.longitude}"
                tvTime.text = getTimeAgo(alert.timestamp)

                // Set priority color
                val priorityColor = when (alert.priority) {
                    AlertMessage.Priority.HIGH -> R.color.priority_high
                    AlertMessage.Priority.MEDIUM -> R.color.priority_medium
                    AlertMessage.Priority.LOW -> R.color.priority_low
                }
                viewPriority.setBackgroundResource(priorityColor)

                // Configure Accept button state
                val isAccepted = alert.isRead
                btnAccept.text = if (isAccepted) "Accepted" else "Accept"
                btnAccept.isEnabled = !isAccepted
                btnAccept.alpha = if (isAccepted) 0.6f else 1.0f

                // Accept handler
                btnAccept.setOnClickListener {
                    if (!isAccepted) {
                        // Optimistically update UI to prevent double taps
                        btnAccept.isEnabled = false
                        btnAccept.text = "Accepted"
                        btnAccept.alpha = 0.6f
                        onAcceptClick(alert)
                    }
                }
            }
        }

        private fun getTimeAgo(timestamp: Long): String {
            val now = System.currentTimeMillis()
            val diff = now - timestamp

            return when {
                diff < 60000 -> "Just now"
                diff < 3600000 -> "${diff / 60000} min ago"
                diff < 86400000 -> "${diff / 3600000} hour ago"
                else -> dateFormat.format(Date(timestamp))
            }
        }
    }

    private class AlertDiffCallback : DiffUtil.ItemCallback<AlertEntity>() {
        override fun areItemsTheSame(oldItem: AlertEntity, newItem: AlertEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlertEntity, newItem: AlertEntity): Boolean {
            return oldItem == newItem
        }
    }
}
