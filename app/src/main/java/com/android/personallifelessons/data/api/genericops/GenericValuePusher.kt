package com.android.personallifelessons.data.api.genericops

import android.util.Log
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.request.PersonalLifeLessonRequest
import com.android.personallifelessons.data.dto.request.UserRequest
import com.android.personallifelessons.data.dto.request.toMap
import com.android.personallifelessons.domain.model.User
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow

/**
 * Adds new data to db
 *
 * Supported convertible value
 * 1. UserRequest
 * 2. PersonalLifeLessonRequest
 * 3. CommentRequest
 * 4. String
 * @param pathReference Reference to topic path ( comments, users ..etc )
 * @param value Value to be pushed
 */
inline fun <reified T> genericValuePusher(
    pathReference: DatabaseReference,
    value: T,
    shouldGenerateKeyForString: Boolean = false
) = callbackFlow<Outcome<String>> {

    val key = pathReference.push().key
    if (key == null) {
        trySendBlocking(Outcome.Error("Unable to generate key for posting!"))
        return@callbackFlow
    }

    val convertedValue =
        try {
            if (T::class == UserRequest::class) {
                val temp = value as UserRequest
                temp.toMap()
            } else if (T::class == PersonalLifeLessonRequest::class) {
                val temp = value as PersonalLifeLessonRequest
                temp.toMap(key)
            } else if (T::class == CommentRequest::class) {
                val temp = value as CommentRequest
                temp.toMap(key)
            }else if(T::class == String::class) {
                mapOf(value as String to value as String)
            }else null
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


    if (T::class == UserRequest::class) {
        val user = value as UserRequest
        pathReference.child(user._id).setValue(convertedValue).addOnSuccessListener {
            trySendBlocking(Outcome.Success(user._id))
        }
            .addOnFailureListener {
                trySendBlocking(Outcome.Error(it.message ?: "Posting unsuccessful"))
            }
    } else if (T::class == String::class) {
        Log.d("result", "Setting $key to $value")
        val temp = value as String
        val keyToPush = if(shouldGenerateKeyForString) key else temp
        pathReference.child(keyToPush).setValue(temp)
            .addOnSuccessListener {
                trySendBlocking(Outcome.Success(key))
            }
            .addOnFailureListener{
                trySendBlocking(Outcome.Error(it.message ?: "Posting unsuccessful"))
            }
    } else {
        pathReference.child(key).setValue(convertedValue).addOnSuccessListener {
            trySendBlocking(Outcome.Success(key))
        }
            .addOnFailureListener {
                trySendBlocking(Outcome.Error(it.message ?: "Posting unsuccessful"))
            }
    }

    awaitClose {}
}