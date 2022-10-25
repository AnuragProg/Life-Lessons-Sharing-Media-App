package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.domain.model.Comment
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    suspend fun getPersonalLifeLesson(
        pid: String
    ): Flow<Outcome<PersonalLifeLesson>>

    suspend fun getComments(
        commentIds: List<String>
    ): Flow<Outcome<List<Comment>>>

    suspend fun updateComment(
        comment: Comment
    ): Flow<Outcome<String>>

    suspend fun postComment(
        comment: CommentRequest,
        pid: String
    ): Flow<Outcome<String>>

    suspend fun deleteComment(
        commentId: String,
        pid: String
    ): Flow<Outcome<String>>
}