package com.example.capstoneproject.ui.account

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.databinding.ItemRowProfileWorkshopBinding
import com.example.capstoneproject.model.WorkshopResponse
import com.example.capstoneproject.ui.form.packet.ExtendPackageActivity

class ProfileWorkshopAdapter(private val workshop: List<WorkshopResponse>, private val token: String): RecyclerView.Adapter<ProfileWorkshopAdapter.ProfileViewHolder>() {

    inner class ProfileViewHolder(private val binding: ItemRowProfileWorkshopBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(workshop: WorkshopResponse) {
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
                binding.btnExtend.isEnabled = workshop.status == "done"
            }
        }
        val button = binding.btnExtend
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
        holder.button.setOnClickListener {
            val intent = Intent(it.context, ExtendPackageActivity::class.java)
            intent.putExtra("workshopId", workshop[position].id)
            intent.putExtra("token", token)
            it.context.startActivity(intent)
        }
    }
}