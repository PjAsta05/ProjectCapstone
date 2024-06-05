package com.example.capstoneproject.ui.workshop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ItemRowWorkshopBinding
import com.example.capstoneproject.model.WorkshopResponse

class WorkshopAdapter(private val workshop: List<WorkshopResponse>): RecyclerView.Adapter<WorkshopAdapter.WorkshopViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class WorkshopViewHolder(private val binding: ItemRowWorkshopBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(workshop: WorkshopResponse) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(workshop)
            }
            binding.apply {
                Glide.with(binding.root)
                    .load(workshop.photo)
                    .error(R.drawable.baseline_image_24)
                    .into(imgItemPhoto)
                tvItemName.text = workshop.workshopName
                tvItemAddress.text = workshop.address
                tvItemDesc.text = workshop.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkshopViewHolder {
        val binding = ItemRowWorkshopBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return WorkshopViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workshop.size
    }

    override fun onBindViewHolder(holder: WorkshopViewHolder, position: Int) {
        holder.bind(workshop[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: WorkshopResponse)
    }
}