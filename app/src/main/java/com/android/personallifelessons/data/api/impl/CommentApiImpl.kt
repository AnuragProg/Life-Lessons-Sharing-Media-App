package com.android.personallifelessons.data.api.impl

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.CommentApi
import com.android.personallifelessons.data.api.components.PathInspector
import com.android.personallifelessons.data.api.genericops.genericValueDeleter
import com.android.personallifelessons.data.api.genericops.genericValueEventListenerFlow
import com.android.personallifelessons.data.api.genericops.genericValuePusher
import com.android.personallifelessons.data.api.genericops.genericValueUpdater
import com.android.personallifelessons.data.components.DBPaths
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.domain.model.Comment
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CommentApiImpl(
    db: DatabaseReference,
) : CommentApi {

    private val pathReference = db.child(DBPaths.COMMENTS)
    override suspend fun getComments(commentIds: List<String>): Flow<Outcome<List<CommentResponse>>> {
        val comments = mutableListOf<CommentResponse>()
        for(id in commentIds){
            val comment = getComment(id).first()
            if(comment is Outcome.Success)
                comments.add(comment.data!!)
        }
        return flow{ emit(Outcome.Success(comments)) }
    }

    override suspend fun getComment(commentId: String): Flow<Outcome<CommentResponse>> {
        return genericValueEventListenerFlow(pathReference.child(commentId))
    }

    override suspend fun postComment(comment: CommentRequest): Flow<Outcome<String>> {
        return genericValuePusher(pathReference, comment)
    }

    override suspend fun updateComment(comment: Comment): Flow<Outcome<String>> {
        val path = pathReference.child(comment._id)
        return PathInspector.pathExistenceCheckWrapper(path){ genericValueUpdater(path, comment) }
    }

    override suspend fun deleteComment(commentId: String): Flow<Outcome<String>> {
        val path = pathReference.child(commentId)
        return PathInspector.pathExistenceCheckWrapper(path){ genericValueDeleter(path) }
    }
}