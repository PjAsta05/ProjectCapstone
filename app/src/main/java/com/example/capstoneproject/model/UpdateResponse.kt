package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @SerializedName("result")
    val result: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: User,
)
