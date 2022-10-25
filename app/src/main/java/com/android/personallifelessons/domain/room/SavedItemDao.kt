package com.android.personallifelessons.domain.room

import androidx.room.*
import com.android.personallifelessons.domain.model.PersonalLifeLessonRoomDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalLifeLesson(pll: PersonalLifeLessonRoomDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonalLifeLessons(plls: List<PersonalLifeLessonRoomDto>)

    @Delete
    suspend fun deletePersonalLifeLesson(pll: PersonalLifeLessonRoomDto)

    @Query("select * from personallifelesson")
    fun getCachedPersonalLifeLessons(): Flow<List<PersonalLifeLessonRoomDto>>

    @Query("delete from personallifelesson where id = :id")
    fun deletePersonalLifeLesson(id: String)

}