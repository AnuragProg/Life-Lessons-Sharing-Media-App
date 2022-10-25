package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.CategoryApi
import com.android.personallifelessons.data.dto.response.toCategory
import com.android.personallifelessons.domain.model.Category
import com.android.personallifelessons.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoryApi: CategoryApi
): CategoryRepository {

    override suspend fun getCategory(categoryId: String): Flow<Outcome<Category>> {
        return categoryApi.getCategory(categoryId).map{
            it.convertTo(::toCategory)
        }
    }

    override suspend fun getAllCategories(): Flow<Outcome<List<Category>>> {
        return categoryApi.getAllCategories().map {
            it.convertListTo(::toCategory)
        }
    }
}