package com.example.capstoneproject.ui.form.packet

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemRowPaketBinding
import com.example.capstoneproject.model.PackageResponse
import java.text.NumberFormat
import java.util.Locale

class PackageAdapter(private val packages: List<PackageResponse>) : RecyclerView.Adapter<PackageAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val binding: ItemRowPaketBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(packageItem: PackageResponse) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(packageItem)
            }
            binding.tvItemId.text = packageItem.id.toString()
            binding.tvPacketName.text = packageItem.packageName
            val formatter = NumberFormat.getNumberInstance(Locale("in", "ID"))
            val formattedPrice = formatter.format(packageItem.price)
            binding.tvPrice.text = "Rp.${formattedPrice}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowPaketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(packages[position])
    }

    override fun getItemCount(): Int {
        return packages.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PackageResponse)
    }
}