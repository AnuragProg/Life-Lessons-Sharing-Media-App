package com.android.personallifelessons.data.api.components

import com.android.personallifelessons.components.Outcome
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


object PathInspector {

    /**
     * Checks for existence of given path reference
     * @param path Reference whose existence needs to be checked
     */
    fun pathExistsWorker(path: DatabaseReference) = callbackFlow<Outcome<Boolean>> {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySendBlocking(Outcome.Success(snapshot.exists()))
            }

            override fun onCancelled(error: DatabaseError) {
                trySendBlocking(Outcome.Error(error.message))
            }
        }

        path.addValueEventListener(listener)
        awaitClose {
            path.removeEventListener(listener)
        }
    }

    /**
     * Used for checking path's existence before doing any operation
     * @param path Reference whose existence needs to be checked
     * @param operation Operation that needs to be done if path exists
     */
    suspend inline fun <T> pathExistenceCheckWrapper(
        path: DatabaseReference,
        operation: () -> Flow<Outcome<T>>,
    ): Flow<Outcome<T>> {
        val exists = pathExistsWorker(path).first()

        if (exists is Outcome.Error)
            return flow {
                emit(Outcome.Error(exists.message!!))
            }

        if (exists is Outcome.Success && !exists.data!!)
            return flow {
                emit(Outcome.Error("No such item exist!"))
            }
        return operation()
    }
}