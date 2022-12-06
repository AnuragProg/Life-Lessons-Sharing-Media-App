package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.request.CommentUpdateRequest
import com.android.personallifelessons.data.dto.response.CommentResponse

interface CommentRepository {
    suspend fun addComment(commentRequest: CommentRequest): Outcome<String>
    suspend fun deleteComment(commentId: String): Outcome<String>
    suspend fun getComments(pllId: String): Outcome<List<CommentResponse>>
    suspend fun updateComment(commentUpdateRequest: CommentUpdateRequest): Outcome<String>
}