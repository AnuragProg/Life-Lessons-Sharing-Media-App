package com.android.personallifelessons.data.dto.response

import com.android.personallifelessons.domain.model.Comment
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CommentResponse(
    val _id: String?=null,
    val userId: String?=null,
    val username: String?=null,
    val comment: String?=null,
    val commentedOn: Long=0
) {
    @Exclude
    fun toComment() = Comment(
        _id = _id!!,
        userId = userId!!,
        username = username!!,
        comment = comment!!,
        commentedOn = commentedOn
    )
}

fun toComment(c: CommentResponse) = Comment(
    _id = c._id!!,
    userId = c.userId!!,
    username = c.username!!,
    comment = c.comment!!,
    commentedOn = c.commentedOn
)
