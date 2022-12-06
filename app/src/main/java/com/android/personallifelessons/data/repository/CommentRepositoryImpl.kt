package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.ApiException
import com.android.personallifelessons.components.GeneralMessages.TOKENNOTFOUND
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.CommentApi
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.request.CommentUpdateRequest
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val userDatastore: UserDatastore
) : CommentRepository{


    override suspend fun addComment(commentRequest: CommentRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = commentApi.addComment(token, commentRequest)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun deleteComment(commentId: String): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = commentApi.deleteComment(token, commentId)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun getComments(pllId: String): Outcome<List<CommentResponse>> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = commentApi.getComments(token, pllId)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!)
        return Outcome.Error(ApiException(response.errorBody()))
    }

    override suspend fun updateComment(commentUpdateRequest: CommentUpdateRequest): Outcome<String> {
        val token = userDatastore.getToken().first() ?: return Outcome.Error(Exception(TOKENNOTFOUND))
        val response = commentApi.updateComment(token, commentUpdateRequest)
        if(response.isSuccessful)
            return Outcome.Success(response.body()!!.message)
        return Outcome.Error(ApiException(response.errorBody()))
    }
}