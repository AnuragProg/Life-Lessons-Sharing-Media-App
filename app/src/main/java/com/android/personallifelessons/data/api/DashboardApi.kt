package com.android.personallifelessons.data.api

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.PersonalLifeLessonRequest
import com.android.personallifelessons.data.dto.response.PersonalLifeLessonResponse
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import kotlinx.coroutines.flow.Flow

interface DashboardApi {

    suspend fun getPersonalLifeLessons(
    ): Flow<Outcome<List<PersonalLifeLessonResponse>>>

    suspend fun getPersonalLifeLesson(
        pid: String
    ): Flow<Outcome<PersonalLifeLessonResponse>>

    suspend fun postPersonalLifeLesson(
        p: PersonalLifeLessonRequest
    ): Flow<Outcome<String>>

    suspend fun updatePersonalLifeLesson(
        p: PersonalLifeLesson
    ): Flow<Outcome<String>>

    suspend fun deletePersonalLifeLesson(
        pid: String
    ): Flow<Outcome<String>>

    suspend fun likePersonalLifeLesson(
        pid: String, userId: String
    ): Flow<Outcome<String>>

    suspend fun dislikePersonalLifeLesson(
        pid: String, userId: String
    ): Flow<Outcome<String>>

    suspend fun addCommentPersonalLifeLesson(
        pid: String, commentId: String
    ): Flow<Outcome<String>>

    suspend fun deleteCommentPersonalLifeLesson(
        pid: String, commentId: String
    ): Flow<Outcome<String>>

}