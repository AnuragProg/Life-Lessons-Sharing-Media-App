package com.android.personallifelessons.data.repository

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.DashboardApi
import com.android.personallifelessons.data.dto.response.toPersonalLifeLesson
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.domain.repository.DashBoardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DashboardRepositoryImpl(
    private val dashboardApi: DashboardApi
) : DashBoardRepository{

    override suspend fun getPersonalLifeLessons(): Flow<Outcome<List<PersonalLifeLesson>>> {
        return dashboardApi.getPersonalLifeLessons().map{
            it.convertListTo(::toPersonalLifeLesson)
        }
    }

    override suspend fun likePersonalLifeLesson(
        pid: String,
        userId: String,
    ): Flow<Outcome<String>> {
        return dashboardApi.likePersonalLifeLesson(pid, userId)
    }

    override suspend fun dislikePersonalLifeLesson(
        pid: String,
        userId: String,
    ): Flow<Outcome<String>> {
        return dashboardApi.dislikePersonalLifeLesson(pid, userId)
    }

    override suspend fun deletePersonalLifeLesson(pid: String): Flow<Outcome<String>> {
        return dashboardApi.deletePersonalLifeLesson(pid)
    }
}