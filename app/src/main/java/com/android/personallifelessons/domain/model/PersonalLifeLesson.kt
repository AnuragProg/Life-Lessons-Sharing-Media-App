package com.android.personallifelessons.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PersonalLifeLesson(
    val _id: String,
    val userId: String,
    val username: String,
    val title: String,
    val learning: String,
    val relatedStory: String,
    val createdOn: Long,
    val categoryId: String,
    val likes: List<String> = emptyList(),   //List of UsersId
    val comments: List<String> = emptyList() //List of commentId
): Parcelable

fun PersonalLifeLesson.toMap() = mapOf(
    "id" to _id,
    "userId" to userId,
    "username" to username,
    "title" to title,
    "learning" to learning,
    "relatedStory" to relatedStory,
    "createdOn" to createdOn,
    "categoryId" to categoryId
)
fun PersonalLifeLesson.toPersonalLifeLessonRoomDto() = PersonalLifeLessonRoomDto(
    id = _id,
    userId = userId,
    username = username,
    title = title,
    learning = learning,
    relatedStory = relatedStory,
    createdOn = createdOn,
    categoryId = categoryId
)