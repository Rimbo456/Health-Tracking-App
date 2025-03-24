package com.example.healthtrackingapp.data.serverUNao

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceUNao {
    @Multipart
    @POST("/predict")
    suspend fun uploadImage(@Part image: MultipartBody.Part): JsonObject
}