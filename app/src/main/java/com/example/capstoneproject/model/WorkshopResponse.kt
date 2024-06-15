package com.example.capstoneproject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class WorkshopResponse(
    @SerializedName("id")
    val id: Int ?= null,

    @SerializedName("paketId")
    val paketId: Int ?= null,

    @SerializedName("userId")
    val userId: Int ?= null,

    @SerializedName("nama_workshop")
    val workshopName: String ?= null,

    @SerializedName("nama_sanggar")
    val sanggarName: String ?= null,

    @SerializedName("alamat")
    val address: String ?= null,

    @SerializedName("email")
    val email: String ?= null,

    @SerializedName("phone")
    val phone: String ?= null,

    @SerializedName("nama_pemilik")
    val owner: String ?= null,

    @SerializedName("deskripsi")
    val description: String ?= null,

    @SerializedName("photo")
    val photo: String ?= null,

    @SerializedName("price")
    val price: Int ?= null,

    @SerializedName("status")
    val status: String ?= null,

    @SerializedName("bukti_pembayaran")
    val proof: String ?= null,

    @SerializedName("paket")
    val paket: @RawValue PackageResponse ?= null
) : Parcelable
