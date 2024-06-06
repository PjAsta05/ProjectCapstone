package com.example.capstoneproject.api

import com.example.capstoneproject.model.AuthResponse
import com.example.capstoneproject.model.BalineseDance
import com.example.capstoneproject.model.PackageResponse
import com.example.capstoneproject.model.UpdateResponse
import com.example.capstoneproject.model.User
import com.example.capstoneproject.model.WorkshopResponse
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
    ): User

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
        @Part("email") email: RequestBody ?= null,
        @Part("password") password: RequestBody ?= null,
        @Part("fullname") fullName: RequestBody ?= null,
        @Part file: MultipartBody.Part ?= null,
        @Header("Authorization") token: String
    ): UpdateResponse

    @GET("tari")
    suspend fun getListTari(
        @Header("Authorization") token: String,
        @Query("nama_tari") name: String,
        @Query("asal_tari") origin: String
    ): List<BalineseDance>

    @GET("tari/{tari}")
    suspend fun findTari(
        @Path("tari") tari: String,
        @Header("Authorization") token: String
    ): BalineseDance

    @GET("paket")
    suspend fun getPackages(
        @Header("Authorization") token: String
    ): List<PackageResponse>

    @GET("workshop")
    suspend fun getWorkshop(
        @Query("status") status: String? = null,
        @Query("userId") userId: Int? = null,
        @Header("Authorization") token: String
    ): List<WorkshopResponse>

    @POST("workshop")
    suspend fun addWorkshop(
        @Part("paketId") packageId: Int,
        @Part("userId") userId: Int,
        @Part("nama_workshop") workshopName: String,
        @Part("nama_sanggar") sanggarName: String,
        @Part("alamat") address: String,
        @Part("email") email: String,
        @Part("phone") phone: String,
        @Part("nama_pemilik") owner: String,
        @Part("deskripsi") description: String,
        @Part("price") price: Int,
        @Part photo: MultipartBody.Part,
        @Part proof: MultipartBody.Part,
        @Header("Authorization") token: String
    ): UpdateResponse

    @PUT("workshop/{id}")
    suspend fun updateWorkshop(
        @Path("id") id: Int,
        @Part("paketId") packageId: Int? = null,
        @Part("userId") userId: Int? = null,
        @Part("nama_workshop") workshopName: String? = null,
        @Part("nama_sanggar") sanggarName: String? = null,
        @Part("alamat") address: String? = null,
        @Part("email") email: String? = null,
        @Part("phone") phone: String? = null,
        @Part("nama_pemilik") owner: String? = null,
        @Part("deskripsi") description: String? = null,
        @Part("price") price: Int? = null,
        @Part photo: MultipartBody.Part? = null,
        @Part proof: MultipartBody.Part? = null,
        @Header("Authorization") token: String
    ): UpdateResponse

    @FormUrlEncoded
    @POST("pendaftaran-workshop")
    suspend fun workshopRegistration(
        @Field("workshopId") workshopId: Int,
        @Field("nama") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("umur") age: Int,
        @Field("jenis_kelamin") gender: String,
        @Header("Authorization") token: String
    ): UpdateResponse
}