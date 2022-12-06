package com.android.personallifelessons.data.api

import com.android.personallifelessons.data.dto.request.PllRequest
import com.android.personallifelessons.data.dto.request.PllUpdateRequest
import com.android.personallifelessons.data.dto.response.GeneralResponse
import com.android.personallifelessons.data.dto.response.PllResponse
import retrofit2.Response
import retrofit2.http.*

interface PLLApi {

    @GET("plls")
    suspend fun getPlls(
        @Header("Authorization") token: String
    ):Response<List<PllResponse>>

    @GET("pll")
    suspend fun getPll(
        @Header("Authorization") token: String,
        @Query("id") pllId: String
    ):Response<PllResponse>

    @POST(".")
    suspend fun postPll(
        @Header("Authorization") token: String,
        @Body pllRequest: PllRequest
    ):Response<GeneralResponse>

    @PATCH(".")
    suspend fun updatePll(
        @Header("Authorization") token: String,
        @Body pllUpdateRequest: PllUpdateRequest
    ):Response<GeneralResponse>

    @POST("like")
    suspend fun likePlls(
        @Header("Authorization") token: String,
        @Body pllIds : List<String>
    ): Response<GeneralResponse>

    @POST("dislike")
    suspend fun dislikePlls(
        @Header("Authorization") token: String,
        @Body pllIds : List<String>
    ): Response<GeneralResponse>

    @DELETE(".")
    suspend fun deletePll(
        @Header("Authorization") token: String,
        @Query("id") pllId: String
    ): Response<GeneralResponse>
}