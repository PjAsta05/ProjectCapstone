package com.example.capstoneproject.ui.account

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemRowProfileWorkshopBinding
import com.example.capstoneproject.model.WorkshopResponse

class ProfileWorkshopAdapter(private val workshop: List<WorkshopResponse>): RecyclerView.Adapter<ProfileWorkshopAdapter.ProfileViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ProfileViewHolder(private val binding: ItemRowProfileWorkshopBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(workshop: WorkshopResponse) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(workshop)
            }
            binding.apply {
                if (workshop.status == "success") {
                    tvStatus.text = "Success"
                } else if (workshop.status == "done") {
                    tvStatus.text = "Inactive"
                } else {
                    tvStatus.text = "Pending"
                }
                tvName.text = workshop.workshopName
                tvOwner.text = workshop.owner
                tvAddress.text = workshop.address
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemRowProfileWorkshopBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ProfileViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return workshop.size
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(workshop[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: WorkshopResponse)
    }
}