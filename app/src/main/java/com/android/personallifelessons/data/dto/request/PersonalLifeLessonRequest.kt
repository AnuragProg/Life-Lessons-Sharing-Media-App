package com.android.personallifelessons.data.dto.request



data class PersonalLifeLessonRequest(
    val userId: String,
    val username: String,
    val title: String,
    val learning: String,
    val relatedStory: String,
    val createdOn: Long,
    val categoryId: String
)

fun PersonalLifeLessonRequest.toMap(_id: String) = mapOf(
    "id" to _id,
    "userId" to userId,
    "username" to username,
    "title" to title,
    "learning" to learning,
    "relatedStory" to relatedStory,
    "createdOn" to createdOn,
    "categoryId" to categoryId
)