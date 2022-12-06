package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.GeneralMessages.TOKENNOTFOUND
import com.android.personallifelessons.components.GeneralMessages.USERIDNOTFOUND
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.PLLApi
import com.android.personallifelessons.data.dto.request.PllRequest
import com.android.personallifelessons.data.dto.request.PllUpdateRequest
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.data.dto.response.PllResponse
import com.android.personallifelessons.data.dto.response.toHashMap
import com.android.personallifelessons.data.dto.response.toPll
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.PllRepository
import kotlinx.coroutines.flow.first

class PllRepositoryImpl(
    private val pllApi: PLLApi,
    private val userDatastore: UserDatastore
) : PllRepository{

    override suspend fun getPlls(): Outcome<List<Pll>> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val userId = userDatastore.getUserId().first() ?: return Outcome.Error(Exception(USERIDNOTFOUND))
        val response = pllApi.getPlls(token)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.map{it.toPll(userId)})
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun getPll(pllId: String): Outcome<Pll> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val userId = userDatastore.getUserId().first() ?: return Outcome.Error(Exception(USERIDNOTFOUND))
        val response = pllApi.getPll(token, pllId)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.toPll(userId))
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun postPll(pllRequest: PllRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = pllApi.postPll(token, pllRequest)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun updatePll(pllUpdateRequest: PllUpdateRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = pllApi.updatePll(token, pllUpdateRequest)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun deletePll(pllId: String): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = pllApi.deletePll(token, pllId)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun likePlls(pllIds: List<String>): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = pllApi.likePlls(token, pllIds)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun dislikePlls(pllIds: List<String>): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = pllApi.dislikePlls(token, pllIds)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }
}