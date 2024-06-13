package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("field")
    val field: String,

    @SerializedName("message")
    val message: String
)
