package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.SignInRequest
import com.android.personallifelessons.data.dto.request.SignUpRequest
import com.android.personallifelessons.data.dto.request.UserUpdateRequest
import com.android.personallifelessons.data.dto.response.User

interface UserRepository {

    suspend fun getUsers(): Outcome<List<User>>
    suspend fun updateUser(userUpdateRequest: UserUpdateRequest): Outcome<String>
    suspend fun deleteUser():Outcome<String>
    suspend fun signUp(signUpRequest: SignUpRequest): Outcome<String>
    suspend fun signInWithToken(): Outcome<String>
    suspend fun signInWithPassword(signInRequest: SignInRequest): Outcome<String>
}