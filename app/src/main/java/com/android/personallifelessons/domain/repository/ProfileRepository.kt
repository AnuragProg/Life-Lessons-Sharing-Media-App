package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.UserRequest
import com.android.personallifelessons.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getCurrentUserInfo(
        userId: String
    ): Flow<Outcome<User>>

    suspend fun updateCurrentUser(
        user: User
    ): Flow<Outcome<String>>

    suspend fun deleteCurrentUser(
        userId: String
    ): Flow<Outcome<String>>

    suspend fun addCurrentUser(
        user: UserRequest
    ): Flow<Outcome<String>>

}