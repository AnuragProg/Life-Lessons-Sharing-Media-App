package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.CommonException
import com.android.personallifelessons.components.GeneralMessages.TOKENNOTFOUND
import com.android.personallifelessons.components.GeneralMessages.USERIDNOTFOUND
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.ServerConnectionError
import com.android.personallifelessons.data.api.PLLApi
import com.android.personallifelessons.data.dto.request.PllRequest
import com.android.personallifelessons.data.dto.request.PllUpdateRequest
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.data.dto.response.toPll
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.PllRepository
import kotlinx.coroutines.flow.first
import java.net.SocketTimeoutException

class PllRepositoryImpl(
    private val pllApi: PLLApi,
    private val userDatastore: UserDatastore
) : PllRepository{

    override suspend fun getPlls(): Outcome<List<Pll>> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        val userId = userDatastore.getUserId().first() ?: return Outcome.Error(CommonException(USERIDNOTFOUND))
        return try{
            val response = pllApi.getPlls(token)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.map{it.toPll(userId)})
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun getPll(pllId: String): Outcome<Pll> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        val userId = userDatastore.getUserId().first() ?: return Outcome.Error(CommonException(USERIDNOTFOUND))
        return try{
            val response = pllApi.getPll(token, pllId)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.toPll(userId))
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun postPll(pllRequest: PllRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = pllApi.postPll(token, pllRequest)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun updatePll(pllUpdateRequest: PllUpdateRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = pllApi.updatePll(token, pllUpdateRequest)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun deletePll(pllId: String): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = pllApi.deletePll(token, pllId)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun likePlls(pllIds: List<String>): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = pllApi.likePlls(token, pllIds)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun dislikePlls(pllIds: List<String>): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = pllApi.dislikePlls(token, pllIds)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }
}