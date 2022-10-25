package com.android.personallifelessons.data.api.impl

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.UserApi
import com.android.personallifelessons.data.api.components.PathInspector
import com.android.personallifelessons.data.api.genericops.genericValueDeleter
import com.android.personallifelessons.data.api.genericops.genericValueEventListenerFlow
import com.android.personallifelessons.data.api.genericops.genericValuePusher
import com.android.personallifelessons.data.api.genericops.genericValueUpdater
import com.android.personallifelessons.data.components.DBPaths
import com.android.personallifelessons.data.dto.request.UserRequest
import com.android.personallifelessons.data.dto.response.UserResponse
import com.android.personallifelessons.domain.model.User
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

class UserApiImpl(
    db: DatabaseReference
) : UserApi{

    private val pathReference = db.child(DBPaths.USER)

    override suspend fun getCurrentUserInfo(userId: String): Flow<Outcome<UserResponse>> {
        return genericValueEventListenerFlow(pathReference.child(userId))
    }

    override suspend fun updateCurrentUser(user: User): Flow<Outcome<String>> {
        val path = pathReference.child(user._id)
        return PathInspector.pathExistenceCheckWrapper(path){ genericValueUpdater(path, user) }
    }

    override suspend fun deleteCurrentUser(userId: String): Flow<Outcome<String>> {
        val path = pathReference.child(userId)
        return PathInspector.pathExistenceCheckWrapper(path){ genericValueDeleter(path) }
    }

    override suspend fun addCurrentUser(user: UserRequest): Flow<Outcome<String>> {
        return genericValuePusher(pathReference, user)
    }
}