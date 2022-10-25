package com.android.personallifelessons.data.api

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentApi {

    suspend fun getComments(
        commentIds: List<String>
    ): Flow<Outcome<List<CommentResponse>>>
    suspend fun getComment(
        commentId: String
    ): Flow<Outcome<CommentResponse>>
    suspend fun postComment(
        comment: CommentRequest
    ): Flow<Outcome<String>>
    suspend fun updateComment(
        comment: Comment
    ): Flow<Outcome<String>>
    suspend fun deleteComment(
        commentId: String
    ): Flow<Outcome<String>>
}