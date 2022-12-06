package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.CategoryRequest
import com.android.personallifelessons.data.dto.request.CategoryUpdateRequest
import com.android.personallifelessons.data.dto.response.CategoryResponse

interface CategoryRepository {
    suspend fun getCategory(categoryId: String): Outcome<CategoryResponse>
    suspend fun getCategories(): Outcome<List<CategoryResponse>>
    suspend fun addCategory(categoryRequest: CategoryRequest): Outcome<String>
    suspend fun deleteCategory(categoryId: String): Outcome<String>
    suspend fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest): Outcome<String>
}