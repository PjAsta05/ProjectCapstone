package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

data class WorkshopResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("paketId")
    val paketId: Int,

    @SerializedName("nama_workshop")
    val workshopName: String,

    @SerializedName("nama_sanggar")
    val sanggarName: String,

    @SerializedName("alamat")
    val address: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("nama_pemilik")
    val owner: String,

    @SerializedName("deskripsi")
    val description: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("status")
    val status: String,

    @SerializedName("bukti_pembayaran")
    val prof: String,

    @SerializedName("paket")
    val paket: PackageResponse
)
