package com.android.personallifelessons.domain.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.PersonalLifeLessonRequest
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun postPersonalLifeLesson(
        p: PersonalLifeLessonRequest
    ): Flow<Outcome<String>>

}