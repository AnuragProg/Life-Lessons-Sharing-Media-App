package com.android.personallifelessons.domain.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDatastore(
    private val context: Context
){
    private val Context.datastore by preferencesDataStore(
        name = "UserDatastore"
    )

    private val tokenKey = stringPreferencesKey("token")
    private val userIdKey = stringPreferencesKey("userId")

    suspend fun addToken(token: String){
        context.datastore.edit{ pref ->
            pref[tokenKey] = token
        }
    }

    fun getToken(): Flow<String?> {
        return context.datastore.data.map{ pref ->
            pref[tokenKey]?.let{token ->
                "Bearer $token"
            }
        }
    }

    suspend fun addUserId(userId: String){
        context.datastore.edit{ pref->
            pref[userIdKey] = userId
        }
    }

    fun getUserId(): Flow<String?>{
        return context.datastore.data.map{ pref->
            pref[userIdKey]
        }
    }

    suspend fun clear(){
        context.datastore.edit{ pref ->
           pref.clear()
        }
    }
}