package com.android.personallifelessons.domain.model


data class User(
    val _id: String,
    val username: String,
    val email: String,
    val photo: String,
    val joinedOn: Long
)
fun User.toMap() = mapOf(
    "id" to _id,
    "username" to username,
    "email" to email,
    "photo" to photo,
    "joinedOn" to joinedOn
)