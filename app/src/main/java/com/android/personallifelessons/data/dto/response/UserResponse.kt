package com.android.personallifelessons.data.dto.response


data class TokenResponse(
    val token: String,
    val userId: String
)

data class User(
    val _id: String,
    val username: String,
    val email: String,
    val joinedOn: Long,
    val isAdmin: Boolean
)