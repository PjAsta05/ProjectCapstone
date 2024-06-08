package com.example.capstoneproject.api

import com.example.capstoneproject.model.AuthResponse
import com.example.capstoneproject.model.BalineseDance
import com.example.capstoneproject.model.PackageResponse
import com.example.capstoneproject.model.UpdateResponse
import com.example.capstoneproject.model.User
import com.example.capstoneproject.model.WorkshopResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
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

    @Multipart
    @POST("workshop")
    suspend fun addWorkshop(
        @Part("paketId") packageId: RequestBody,
        @Part("userId") userId: RequestBody,
        @Part("nama_workshop") workshopName: RequestBody,
        @Part("nama_sanggar") sanggarName: RequestBody,
        @Part("alamat") address: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("nama_pemilik") owner: RequestBody,
        @Part("deskripsi") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part photo: MultipartBody.Part? = null,
        @Part proof: MultipartBody.Part? = null,
        @Header("Authorization") token: String
    ): UpdateResponse

    @Multipart
    @PUT("workshop/{id}")
    suspend fun updateWorkshop(
        @Path("id") id: Int,
        @Part("paketId") packageId: RequestBody? = null,
        @Part("userId") userId: RequestBody? = null,
        @Part("nama_workshop") workshopName: RequestBody? = null,
        @Part("nama_sanggar") sanggarName: RequestBody? = null,
        @Part("alamat") address: RequestBody? = null,
        @Part("email") email: RequestBody? = null,
        @Part("phone") phone: RequestBody? = null,
        @Part("nama_pemilik") owner: RequestBody? = null,
        @Part("deskripsi") description: RequestBody? = null,
        @Part("price") price: RequestBody? = null,
        @Part("status") status: RequestBody? = null,
        @Part photo: MultipartBody.Part? = null,
        @Part proof: MultipartBody.Part? = null,
        @Header("Authorization") token: String
    ): UpdateResponse

    @Multipart
    @PUT("workshop/extend/{id}")
    suspend fun extendWorkshop(
        @Path("id") id: Int,
        @Part("paketId") packageId: RequestBody,
        @Part proof: MultipartBody.Part? = null,
        @Header("Authorization") token: String
    ): UpdateResponse

    @DELETE("workshop/{id}")
    suspend fun deleteWorkshop(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): UpdateResponse

    @GET("workshop/{id}")
    suspend fun getWorkshopById(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): WorkshopResponse

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