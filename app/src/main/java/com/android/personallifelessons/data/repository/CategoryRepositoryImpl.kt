package com.android.personallifelessons.data.repository

import android.util.Log
import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.GeneralMessages.TOKENNOTFOUND
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.CategoryApi
import com.android.personallifelessons.data.dto.request.CategoryRequest
import com.android.personallifelessons.data.dto.request.CategoryUpdateRequest
import com.android.personallifelessons.data.dto.response.CategoryResponse
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryRepositoryImpl(
    private val categoryApi: CategoryApi,
    private val userDatastore: UserDatastore
) : CategoryRepository{

    override suspend fun getCategory(categoryId: String): Outcome<CategoryResponse> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = categoryApi.getCategory(token, categoryId)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun getCategories(): Outcome<List<CategoryResponse>> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = categoryApi.getCategories(token)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun addCategory(categoryRequest: CategoryRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = categoryApi.addCategory(token, categoryRequest)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun deleteCategory(categoryId: String): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = categoryApi.deleteCategory(token, categoryId)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = categoryApi.updateCategory(token, categoryUpdateRequest)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }
}