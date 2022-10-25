package com.android.personallifelessons.data.dto.request

data class CommentRequest(
    val userId: String,
    val username: String,
    val comment: String,
    val commentedOn: Long
)
fun CommentRequest.toMap(_id: String) = mapOf(
    "id" to _id,
    "userId" to userId,
    "username" to username,
    "comment" to comment,
    "commentedOn" to commentedOn
)