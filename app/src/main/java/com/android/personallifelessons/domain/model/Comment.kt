package com.android.personallifelessons.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val _id: String,
    val userId: String,
    val username: String,
    val comment: String,
    val commentedOn: Long
)

fun Comment.toMap() = mapOf(
    "id" to _id,
    "userId" to userId,
    "username" to username,
    "comment" to comment,
    "commentedOn" to commentedOn
)