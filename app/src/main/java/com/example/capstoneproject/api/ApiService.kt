package com.example.capstoneproject.api

import com.example.capstoneproject.model.AuthResponse
import com.example.capstoneproject.model.ListTariResponse
import com.example.capstoneproject.model.UpdateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("fullname") fullname: String
    ): Boolean

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @Multipart
    @PUT("user/{id}")
    suspend fun updateUserProfile(
        @Path("id") id: Int,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("fullname") fullName: RequestBody,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): UpdateResponse

    @GET("tari")
    suspend fun getListTari(
        @Header("Authorization") token: String,
        @Query("nama_tari") name: String,
        @Query("asal_tari") origin: String
    ): ListTariResponse
}