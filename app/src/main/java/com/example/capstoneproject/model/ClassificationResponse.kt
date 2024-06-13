package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

data class ClassificationResponse(
    @SerializedName("status")
    val result: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: Predict
)

data class Predict(
    @SerializedName("result")
    val label: String,

    @SerializedName("confidenceScore")
    val confidence: Double
)
