package com.android.personallifelessons.data.api.genericops

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.Comment
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.domain.model.User
import com.android.personallifelessons.domain.model.toMap
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

/**
 * Updates existing data in db
 *
 * Supported convertible (to Map) value
 * 1. User
 * 2. PersonalLifeLesson
 * 3. Comment
 * @param pathReference (ID)Reference to path where the value needs to be updated
 * @param value New Value
 */
inline fun <reified T> genericValueUpdater(
    pathReference: DatabaseReference,
    value: T,
) = callbackFlow<Outcome<String>> {
    var key : String? = null
    val convertedValue =
        try {
            if (T::class == User::class) {
                val temp = value as User
                key = temp._id
                temp.toMap()
            } else if (T::class == PersonalLifeLesson::class) {
                val temp = value as PersonalLifeLesson
                key = temp._id
                temp.toMap()
            } else if (T::class == Comment::class) {
                val temp = value as Comment
                key = temp._id
                temp.toMap()
            } else null
        } catch (e: Exception) {
            trySendBlocking(
                Outcome.Error(e.message ?: "Trying to send corrupted data!")
            )
            return@callbackFlow
        }

    if (convertedValue == null) {
        trySendBlocking(Outcome.Error("Data not converted to deliverable format!"))
        this.cancel()
        return@callbackFlow
    }

    pathReference.setValue(convertedValue)
        .addOnSuccessListener {
            trySendBlocking(Outcome.Success(key!!))
        }
        .addOnFailureListener {
            trySendBlocking(Outcome.Error(it.message ?: "Posting unsuccessful"))
        }

    awaitClose {}
}