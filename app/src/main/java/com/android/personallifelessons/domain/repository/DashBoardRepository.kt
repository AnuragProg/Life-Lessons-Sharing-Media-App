package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import kotlinx.coroutines.flow.Flow

interface DashBoardRepository {

    suspend fun getPersonalLifeLessons(
    ): Flow<Outcome<List<PersonalLifeLesson>>>

    suspend fun likePersonalLifeLesson(
        pid: String, userId: String
    ): Flow<Outcome<String>>

    suspend fun dislikePersonalLifeLesson(
        pid: String, userId: String
    ): Flow<Outcome<String>>

    suspend fun deletePersonalLifeLesson(
        pid: String
    ): Flow<Outcome<String>>

}