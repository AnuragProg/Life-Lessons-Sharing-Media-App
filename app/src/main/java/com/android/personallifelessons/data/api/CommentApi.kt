package com.android.personallifelessons.data.api

import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.request.CommentUpdateRequest
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.data.dto.response.GeneralResponse
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {

    @POST(".")
    suspend fun addComment(
        @Header("Authorization") token: String,
        @Body commentRequest: CommentRequest
    ): Response<GeneralResponse>

    @DELETE(".")
    suspend fun deleteComment(
        @Header("Authorization") token :String,
        @Query("id") commentId: String
    ): Response<GeneralResponse>

    @GET(".")
    suspend fun getComments(
        @Header("Authorization") token: String,
        @Query("id") pllId: String
    ): Response<List<CommentResponse>>

    @PATCH(".")
    suspend fun updateComment(
        @Header("Authorization") token: String,
        @Body commentUpdateRequest: CommentUpdateRequest
    ): Response<GeneralResponse>
}