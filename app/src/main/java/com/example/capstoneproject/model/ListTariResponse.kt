package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

class ListTariResponse (
    @SerializedName("ListTari")
    val listTari: List<Tari>,
    @SerializedName("token")
    val token: String,
)

data class Tari (
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama_tari")
    val namaTari: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("asal_tari")
    val asalTari: String,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("url_gambar")
    val urlGambar: String,
    @SerializedName("url_video")
    val urlVideo: String,
    @SerializedName("createdAt")
    val createdAt: String
)