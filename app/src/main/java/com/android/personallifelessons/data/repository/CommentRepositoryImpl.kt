package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.GeneralMessages.TOKENNOTFOUND
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.components.ServerConnectionError
import com.android.personallifelessons.data.api.CommentApi
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.request.CommentUpdateRequest
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.CommentRepository
import kotlinx.coroutines.flow.first
import java.net.SocketTimeoutException

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val userDatastore: UserDatastore
) : CommentRepository{


    override suspend fun addComment(commentRequest: CommentRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        return try{
            val response = commentApi.addComment(token, commentRequest)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun deleteComment(commentId: String): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        return try{
            val response = commentApi.deleteComment(token, commentId)
            if(response.isSuccessful)
                 Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun getComments(pllId: String): Outcome<List<CommentResponse>> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        return try{
            val response = commentApi.getComments(token, pllId)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!)
            else Outcome.Error(ApiException(response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }

    override suspend fun updateComment(commentUpdateRequest: CommentUpdateRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        return try{
            val response = commentApi.updateComment(token, commentUpdateRequest)
            if(response.isSuccessful)
                Outcome.Success(response.body()!!.message)
            else Outcome.Error(ApiException(response.errorBody()))
        }catch(e : SocketTimeoutException){
            Outcome.Error(ServerConnectionError("Unable to connect to server"))
        }
    }
}