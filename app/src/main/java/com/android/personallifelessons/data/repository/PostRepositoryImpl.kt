package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.DashboardApi
import com.android.personallifelessons.data.dto.request.PersonalLifeLessonRequest
import com.android.personallifelessons.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(
    private val dashboardApi: DashboardApi
) : PostRepository {

    override suspend fun postPersonalLifeLesson(p: PersonalLifeLessonRequest): Flow<Outcome<String>> {
        return dashboardApi.postPersonalLifeLesson(p)
    }
}