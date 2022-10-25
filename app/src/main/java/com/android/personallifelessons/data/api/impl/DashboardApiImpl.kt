package com.android.personallifelessons.data.api.impl

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.DashboardApi
import com.android.personallifelessons.data.api.components.PathInspector
import com.android.personallifelessons.data.api.genericops.*
import com.android.personallifelessons.data.components.DBPaths
import com.android.personallifelessons.data.dto.request.PersonalLifeLessonRequest
import com.android.personallifelessons.data.dto.response.PersonalLifeLessonResponse
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class DashboardApiImpl(
    db: DatabaseReference,
) : DashboardApi {

    private val pathReference = db.child(DBPaths.PLL)

    override suspend fun getPersonalLifeLesson(pid: String): Flow<Outcome<PersonalLifeLessonResponse>> {
        val path = pathReference.child(pid)
        var likesUserIdList = emptyList<String>()
        var commentIdList = emptyList<String>()
        val lifeLessonsWithoutLikesAndComments =
            genericValueEventListenerFlow<PersonalLifeLessonResponse>(path).first()
        if (lifeLessonsWithoutLikesAndComments is Outcome.Error)
            return flow { emit(lifeLessonsWithoutLikesAndComments) }
        val likes = genericListValueEventListenerFlow<String>(path.child(DBPaths.PLL_LIKES)).first()
        if (likes is Outcome.Success)
            likesUserIdList = likes.data!!
        val comments =
            genericListValueEventListenerFlow<String>(path.child(DBPaths.PLL_COMMENTS)).first()
        if (comments is Outcome.Success)
            commentIdList = comments.data!!
        return flow {
            lifeLessonsWithoutLikesAndComments.data!!.comments = commentIdList
            lifeLessonsWithoutLikesAndComments.data.likes = likesUserIdList
            emit(lifeLessonsWithoutLikesAndComments)
        }
    }

    override suspend fun getPersonalLifeLessons(): Flow<Outcome<List<PersonalLifeLessonResponse>>> {
        val personalLifeLessonResponseList = mutableListOf<PersonalLifeLessonResponse>()
        val pllIds = firebaseRealtimeDBKeyExtractor(pathReference).first()
        if (pllIds is Outcome.Error)
            return flow { emit(Outcome.Error(pllIds.message!!)) }
        for (pllId in pllIds.data!!) {
            val pll = getPersonalLifeLesson(pllId).first()
            if (pll is Outcome.Error) continue
            personalLifeLessonResponseList.add(pll.data!!)
        }
        return flow { emit(Outcome.Success(personalLifeLessonResponseList)) }
    }

    override suspend fun postPersonalLifeLesson(p: PersonalLifeLessonRequest): Flow<Outcome<String>> {
        return genericValuePusher(pathReference, p)
    }

    override suspend fun updatePersonalLifeLesson(p: PersonalLifeLesson): Flow<Outcome<String>> {
        val path = pathReference.child(p._id)
        return PathInspector.pathExistenceCheckWrapper(path) { genericValueUpdater(path, p) }
    }

    override suspend fun deletePersonalLifeLesson(pid: String): Flow<Outcome<String>> {
        val path = pathReference.child(pid)
        return PathInspector.pathExistenceCheckWrapper(path) { genericValueDeleter(path) }
    }

    override suspend fun likePersonalLifeLesson(pid: String, userId: String): Flow<Outcome<String>> {
        val path = pathReference.child(pid).child(DBPaths.PLL_LIKES)
        return genericValuePusher(path, userId)
    }

    override suspend fun dislikePersonalLifeLesson(pid: String, userId: String): Flow<Outcome<String>> {
        val path = pathReference.child(pid).child(DBPaths.PLL_LIKES).child(userId)
        return PathInspector.pathExistenceCheckWrapper(path){ genericValueDeleter(path) }
    }

    override suspend fun addCommentPersonalLifeLesson(pid: String, commentId: String): Flow<Outcome<String>> {
        val path = pathReference.child(pid).child(DBPaths.COMMENTS)
        return genericValuePusher(path, commentId)
    }

    override suspend fun deleteCommentPersonalLifeLesson(pid: String, commentId: String): Flow<Outcome<String>> {
        val path = pathReference.child(pid).child(DBPaths.COMMENTS).child(commentId)
        return PathInspector.pathExistenceCheckWrapper(path){ genericValueDeleter(path) }
    }
}