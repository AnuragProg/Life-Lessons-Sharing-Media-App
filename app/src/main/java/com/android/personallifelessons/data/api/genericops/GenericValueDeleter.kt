package com.android.personallifelessons.data.api.genericops

import com.android.personallifelessons.components.Outcome
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

/**
 * Deletes existing data from db
 * @param pathReference (ID)Reference to path where the value needs to be updated
 */
fun genericValueDeleter(
    pathReference: DatabaseReference,
) = callbackFlow<Outcome<String>> {
    pathReference.removeValue()
        .addOnSuccessListener {
            trySendBlocking(Outcome.Success(pathReference.key!!))
        }
        .addOnFailureListener {
            trySendBlocking(Outcome.Error(it.message ?: "Post deletion unsuccessful"))
        }
    awaitClose {}
}