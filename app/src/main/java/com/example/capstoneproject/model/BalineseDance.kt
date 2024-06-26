package com.example.capstoneproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BalineseDance(
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
) :Parcelable
