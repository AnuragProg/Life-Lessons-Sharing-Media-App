package com.android.personallifelessons.data.api.genericops

import com.android.personallifelessons.components.Outcome
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

/**
 * Fetches and listens to single data object
 *
 * Note:
 *
 * Make sure that the fields are flattened and does not contain nesting
 *
 * If fields like list's or nested objects exist, exclude them
 *
 * set:Exclude, get:Exclude and Exclude annotation
 * @param pathReference (ID)Reference to the single data object
 */
inline fun <reified T> genericValueEventListenerFlow(
    pathReference: DatabaseReference,
) = callbackFlow<Outcome<T>> {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (!snapshot.exists()) trySendBlocking(Outcome.Error("No such field exist!"))
            try {
                val response = snapshot.getValue<T>()
                if (response == null) {
                    trySendBlocking(Outcome.Error("Unable to extract data!"))
                    return
                }
                trySendBlocking(Outcome.Success(response))
            } catch (e: Exception) {
                trySendBlocking(Outcome.Error(e.message!!))
            }
        }

        override fun onCancelled(error: DatabaseError) {
            trySendBlocking(Outcome.Error(error.message))
        }
    }
    pathReference.addValueEventListener(listener)
    awaitClose {
        pathReference.removeEventListener(listener)
    }
}

/**
 * Fetches and listens to list of data objects
 * @param pathReference Reference to topic path ( comments, users ..etc )
 */
inline fun <reified T> genericListValueEventListenerFlow(
    pathReference: DatabaseReference,
) = callbackFlow<Outcome<List<T>>> {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (!snapshot.exists()) trySendBlocking(Outcome.Error("No such field exist!"))
            val collection = snapshot.children
            try {
                val result = mutableListOf<T>()
                for (data in collection) {
                    val convertedValue = data.getValue<T>() ?: continue
                    result.add(convertedValue)
                }
                trySendBlocking(Outcome.Success(result.toList()))
            } catch (e: Exception) {
                trySendBlocking(Outcome.Error(e.message!!))
            }
        }

        override fun onCancelled(error: DatabaseError) {
            trySendBlocking(Outcome.Error(error.message))
        }
    }

    pathReference.addValueEventListener(listener)

    awaitClose {
        pathReference.removeEventListener(listener)
    }
}

/**
 * Fetches and listens to keys under the given path reference
 * @param pathReference Reference to topic path ( comments, users ..etc )
 * @param orderByChildQuery relative path to field according to which data should be fetched e.g createdOn
 */
fun firebaseRealtimeDBKeyExtractor(
    pathReference: DatabaseReference,
    orderByChildQuery: String? = null
) = callbackFlow<Outcome<List<String>>> {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (!snapshot.exists()) trySendBlocking(Outcome.Error("No such field exist!"))
            val collection = snapshot.children
            try {
                val result = mutableListOf<String>()
                for (data in collection) {
                    val convertedValue = data.key ?: continue
                    result.add(convertedValue)
                }
                trySendBlocking(Outcome.Success(result.toList()))
            } catch (e: Exception) {
                trySendBlocking(Outcome.Error(e.message!!))
            }

        }

        override fun onCancelled(error: DatabaseError) {
            trySendBlocking(Outcome.Error(error.message))
        }
    }
    val query = orderByChildQuery?.let{pathReference.orderByChild(orderByChildQuery)}
    if(query!=null)
        query.addValueEventListener(listener)
    else
        pathReference.addValueEventListener(listener)
    awaitClose {
        if(query!=null){
            query.removeEventListener(listener)
        }else
            pathReference.removeEventListener(listener)
    }
}