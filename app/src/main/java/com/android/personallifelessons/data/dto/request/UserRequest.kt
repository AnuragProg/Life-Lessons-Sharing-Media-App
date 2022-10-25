package com.android.personallifelessons.data.dto.request

data class UserRequest(
    val _id: String,
    val username: String,
    val email: String,
    val photo: String,
    val joinedOn: Long
)

fun UserRequest.toMap() = mapOf(
    "id" to _id,
    "username" to username,
    "email" to email,
    "photo" to photo,
    "joinedOn" to joinedOn
)
