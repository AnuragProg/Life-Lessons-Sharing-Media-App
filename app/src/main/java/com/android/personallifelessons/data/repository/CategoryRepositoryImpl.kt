package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.CommonException
import com.android.personallifelessons.components.GeneralMessages.TOKENNOTFOUND
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.ServerConnectionError
import com.android.personallifelessons.data.api.CategoryApi
import com.android.personallifelessons.data.dto.request.CategoryRequest
import com.android.personallifelessons.data.dto.request.CategoryUpdateRequest
import com.android.personallifelessons.data.dto.response.CategoryResponse
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.first
import java.net.SocketTimeoutException

class CategoryRepositoryImpl(
    private val categoryApi: CategoryApi,
    private val userDatastore: UserDatastore
) : CategoryRepository{

    override suspend fun getCategory(categoryId: String): Outcome<CategoryResponse> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = categoryApi.getCategory(token, categoryId)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!)
            else
                Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun getCategories(): Outcome<List<CategoryResponse>> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = categoryApi.getCategories(token)
            if(response.isSuccessful)
                return Outcome.Success(response.body()!!)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun addCategory(categoryRequest: CategoryRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = categoryApi.addCategory(token, categoryRequest)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(),response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun deleteCategory(categoryId: String): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = categoryApi.deleteCategory(token, categoryId)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun updateCategory(categoryUpdateRequest: CategoryUpdateRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = categoryApi.updateCategory(token, categoryUpdateRequest)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }
}