package com.example.capstoneproject.ui.form.packet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemRowPaketBinding
import com.example.capstoneproject.model.PackageResponse

class PackageAdapter : RecyclerView.Adapter<PackageAdapter.ViewHolder>() {

    private var packages: List<PackageResponse> = listOf()

    inner class ViewHolder(private val binding: ItemRowPaketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(packageItem: PackageResponse) {
            binding.tvPacketName.text = packageItem.packageName
            binding.tvPrice.text = "Rp. ${packageItem.price}"
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

    fun submitList(packages: List<PackageResponse>) {
        this.packages = packages
        notifyDataSetChanged()
    }
}