package com.android.personallifelessons.data.api

import com.android.personallifelessons.data.dto.request.SignInRequest
import com.android.personallifelessons.data.dto.request.SignUpRequest
import com.android.personallifelessons.data.dto.request.UserUpdateRequest
import com.android.personallifelessons.data.dto.response.GeneralResponse
import com.android.personallifelessons.data.dto.response.TokenResponse
import com.android.personallifelessons.data.dto.response.User
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET(".")
    suspend fun getUsers(
        @Header("Authorization") token:String,
    ): Response<List<User>>

    @PATCH(".")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body userUpdateRequest: UserUpdateRequest
    ): Response<GeneralResponse>

    @DELETE(".")
    suspend fun deleteUser(
        @Header("Authorization") token: String
    ): Response<GeneralResponse>

    @POST("signIn")
    suspend fun signInWithToken(
        @Header("Authorization") token: String,
    ): Response<TokenResponse>

    // Requires not authentication token
    @POST("signInWithPassword")
    suspend fun signInWithPassword(
        @Body signInRequest: SignInRequest
    ): Response<TokenResponse>

    @POST("signUp")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<TokenResponse>
}