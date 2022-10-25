package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.CommentApi
import com.android.personallifelessons.data.api.DashboardApi
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.response.toComment
import com.android.personallifelessons.data.dto.response.toPersonalLifeLesson
import com.android.personallifelessons.domain.model.Comment
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CommentRepositoryImpl(
    private val commentApi: CommentApi,
    private val dashboardApi: DashboardApi,
) : CommentRepository {

    override suspend fun getPersonalLifeLesson(pid: String): Flow<Outcome<PersonalLifeLesson>> {
        return dashboardApi.getPersonalLifeLesson(pid).map {
            it.convertTo(::toPersonalLifeLesson)
        }
    }

    override suspend fun getComments(commentIds: List<String>): Flow<Outcome<List<Comment>>> {
        return commentApi.getComments(commentIds).map {
            it.convertListTo(::toComment)
        }
    }

    override suspend fun updateComment(comment: Comment): Flow<Outcome<String>> {
        return commentApi.updateComment(comment)
    }

    override suspend fun postComment(
        comment: CommentRequest,
        pid: String
    ): Flow<Outcome<String>> {
        return commentApi.postComment(comment).flatMapConcat {
            when (it) {
                is Outcome.Error, is Outcome.Loading -> flow { emit(it) }
                is Outcome.Success -> dashboardApi.addCommentPersonalLifeLesson(pid, it.data!!)
            }
        }
    }

    override suspend fun deleteComment(
        commentId: String, pid: String,
    ): Flow<Outcome<String>> {
        return commentApi.deleteComment(commentId).flatMapConcat {
            when(it){
                is Outcome.Error,is Outcome.Loading -> flow{emit(it)}
                is Outcome.Success -> dashboardApi.deleteCommentPersonalLifeLesson(pid, commentId)
            }
        }
    }
}