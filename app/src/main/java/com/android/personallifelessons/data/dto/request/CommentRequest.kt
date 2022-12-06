package com.android.personallifelessons.data.dto.request

data class CommentRequest(
    val pllId: String,
    val comment: String,
)

data class CommentUpdateRequest(
    val _id: String,
    val comment: String
)