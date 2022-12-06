package com.android.personallifelessons.data.api

import com.android.personallifelessons.data.dto.request.CategoryRequest
import com.android.personallifelessons.data.dto.request.CategoryUpdateRequest
import com.android.personallifelessons.data.dto.response.CategoryResponse
import com.android.personallifelessons.data.dto.response.GeneralResponse
import retrofit2.Response
import retrofit2.http.*

interface CategoryApi {

    @GET("category")
    suspend fun getCategory(
        @Header("Authorization") token: String,
        @Query("id") categoryId: String
    ): Response<CategoryResponse>

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): Response<List<CategoryResponse>>

    @POST(".")
    suspend fun addCategory(
        @Header("Authorization") token :String,
        @Body categoryRequest: CategoryRequest
    ): Response<GeneralResponse>

    @DELETE(".")
    suspend fun deleteCategory(
        @Header("Authorization") token: String,
        @Query("id") categoryId : String
    ): Response<GeneralResponse>

    @PATCH(".")
    suspend fun updateCategory(
        @Header("Authorization") token: String,
        @Body categoryUpdateRequest: CategoryUpdateRequest
    ): Response<GeneralResponse>
}