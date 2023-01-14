package com.android.personallifelessons.data.repository

import android.util.Log
import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.CommonException
import com.android.personallifelessons.components.GeneralMessages.SUCCESSFULLYSIGNEDIN
import com.android.personallifelessons.components.GeneralMessages.SUCCESSFULLYSIGNEDUP
import com.android.personallifelessons.components.GeneralMessages.TOKENNOTFOUND
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.ServerConnectionError
import com.android.personallifelessons.data.api.UserApi
import com.android.personallifelessons.data.dto.request.SignInRequest
import com.android.personallifelessons.data.dto.request.SignUpRequest
import com.android.personallifelessons.data.dto.request.UserUpdateRequest
import com.android.personallifelessons.data.dto.response.User
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import java.net.SocketTimeoutException

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDatastore: UserDatastore
) : UserRepository{

    override suspend fun getUsers(): Outcome<List<User>> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = userApi.getUsers(token)
            Log.d("response", response.body()!!.toString())
            if(response.isSuccessful)
                Outcome.Success(response.body()!!)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun updateUser(userUpdateRequest: UserUpdateRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = userApi.updateUser(token, userUpdateRequest)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun deleteUser(): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = userApi.deleteUser(token)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.code(),response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Outcome<String> {
        return try{
            val response = userApi.signUp(signUpRequest)
            if(response.isSuccessful){
                userDatastore.addUserId(response.body()!!.userId)
                userDatastore.addToken(response.body()!!.token)
                Outcome.Success(SUCCESSFULLYSIGNEDUP)
            }
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun signInWithToken(): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(CommonException(TOKENNOTFOUND))
        return try{
            val response = userApi.signInWithToken(token)
            if(response.isSuccessful){
                userDatastore.addUserId(response.body()!!.userId)
                userDatastore.addToken(response.body()!!.token)
                return Outcome.Success(SUCCESSFULLYSIGNEDIN)
            }
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun signInWithPassword(signInRequest: SignInRequest): Outcome<String> {
        return try{
            val response = userApi.signInWithPassword(signInRequest)
            if(response.isSuccessful){
                userDatastore.addUserId(response.body()!!.userId)
                userDatastore.addToken(response.body()!!.token)
                return Outcome.Success(SUCCESSFULLYSIGNEDIN)
            }
            else Outcome.Error(ApiException(response.code(), response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }
}