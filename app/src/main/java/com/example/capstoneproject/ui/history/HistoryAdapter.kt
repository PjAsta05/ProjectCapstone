package com.example.capstoneproject.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.database.History
import com.example.capstoneproject.databinding.ItemRowHistoryBinding

class HistoryAdapter(private val history: List<History>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class HistoryViewHolder(private val binding: ItemRowHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(history)
            }
            binding.apply {
                tvWorkshop.text = history.workshopName
                tvDate.text = history.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemRowHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return history.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(history[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: History)
    }
}