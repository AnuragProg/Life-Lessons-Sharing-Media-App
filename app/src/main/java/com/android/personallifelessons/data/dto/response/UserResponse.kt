package com.android.personallifelessons.data.dto.response

import com.android.personallifelessons.domain.model.User
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserResponse(
    val _id: String?=null,
    val username: String?=null,
    val email: String?=null,
    val photo: String?=null,
    val joinedOn: Long=0
) {

    @Exclude
    fun toUser() = User(
        _id = _id!!,
        username = username!!,
        email = email!!,
        photo = photo!!,
        joinedOn = joinedOn
    )
}

fun toUser(u: UserResponse) = User(
    _id = u._id!!,
    username = u.username!!,
    email = u.email!!,
    photo = u.photo!!,
    joinedOn = u.joinedOn
)