package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategory(
        categoryId: String
    ): Flow<Outcome<Category>>

    suspend fun getAllCategories(
    ): Flow<Outcome<List<Category>>>

}