package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

data class PackageResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama_paket")
    val packageName: String,
    @SerializedName("price")
    val price: Int,
)
