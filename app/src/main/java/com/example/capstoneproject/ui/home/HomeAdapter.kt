package com.example.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.ItemRowTariBinding
import com.example.capstoneproject.model.Tari

class HomeAdapter(private val tari: List<Tari>) : RecyclerView.Adapter<HomeAdapter.TariViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TariViewHolder {
        val binding = ItemRowTariBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TariViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TariViewHolder, position: Int) {
        holder.bind(tari[position])
    }

    inner class TariViewHolder(private val binding: ItemRowTariBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tari: Tari) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(tari)
            }
            binding.apply {
                Glide.with(binding.root)
                    .load(tari.urlGambar)
                    .error(R.drawable.baseline_image_24)
                    .into(ivTari)
                tvItemTari.text = tari.namaTari
                tvItemOrigin.text = tari.asalTari
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Tari)
    }
}