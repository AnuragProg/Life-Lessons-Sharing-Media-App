package com.android.personallifelessons.data.dto.response

data class CommentResponse(
    val _id: String,
    val pllId: String,
    val userId: String,
    val username: String,
    val comment: String,
    val commentedOn: Long
)
