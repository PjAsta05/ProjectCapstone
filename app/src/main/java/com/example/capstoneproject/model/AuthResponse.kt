package com.example.capstoneproject.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("user")
    val user: User,
    @SerializedName("token")
    val token: String,
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("fullname")
    val fullname: String,

    @SerializedName("photo")
    val photo: String,

    @SerializedName("role")
    val role: String,
)
