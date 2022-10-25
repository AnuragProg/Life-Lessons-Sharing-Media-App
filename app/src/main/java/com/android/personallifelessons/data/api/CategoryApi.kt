package com.android.personallifelessons.data.api

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.response.CategoryResponse
import kotlinx.coroutines.flow.Flow

interface CategoryApi {

    suspend fun getCategory(
        categoryId: String
    ): Flow<Outcome<CategoryResponse>>
    suspend fun getAllCategories(
    ): Flow<Outcome<List<CategoryResponse>>>
}