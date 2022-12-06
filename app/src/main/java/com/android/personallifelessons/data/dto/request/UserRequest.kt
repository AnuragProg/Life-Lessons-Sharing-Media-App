package com.android.personallifelessons.data.dto.request


data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String,
    val photo: String
)

data class UserUpdateRequest(
    val username: String,
    val password: String,
    val photo: String,
)

data class SignInRequest(
    val email: String,
    val password: String
)