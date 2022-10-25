package com.android.personallifelessons.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "PersonalLifeLesson")
class PersonalLifeLessonRoomDto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val userId: String,
    val username: String,
    val title: String,
    val learning: String,
    val relatedStory: String,
    val createdOn: Long,
    val categoryId: String,
)
