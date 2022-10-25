package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.UserApi
import com.android.personallifelessons.data.dto.request.UserRequest
import com.android.personallifelessons.data.dto.response.toUser
import com.android.personallifelessons.domain.model.User
import com.android.personallifelessons.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl(
    private val userApi: UserApi
) : ProfileRepository{

    override suspend fun getCurrentUserInfo(userId: String): Flow<Outcome<User>> {
        return userApi.getCurrentUserInfo(userId).map{
            it.convertTo(::toUser)
        }
    }

    override suspend fun updateCurrentUser(user: User): Flow<Outcome<String>> {
        return userApi.updateCurrentUser(user)
    }

    override suspend fun deleteCurrentUser(userId: String): Flow<Outcome<String>> {
        return userApi.deleteCurrentUser(userId)
    }

    override suspend fun addCurrentUser(user: UserRequest): Flow<Outcome<String>> {
        return userApi.addCurrentUser(user)
    }
}