package com.android.personallifelessons.data.dto.response

import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class PersonalLifeLessonResponse(
    val _id: String?=null,
    val userId: String?=null,
    val username: String?=null,
    val title: String?=null,
    val learning: String?=null,
    val relatedStory: String?=null,
    val createdOn: Long = 0,
    val categoryId: String?=null,
    @get:Exclude
    @set:Exclude
    @Exclude
    var likes: List<String>?=null,   //List of UsersId
    @get:Exclude
    @set:Exclude
    @Exclude
    var comments: List<String>?=null //List of commentId
){
    @Exclude
    fun toPersonalLifeLesson() = PersonalLifeLesson(
        _id = _id!!,
        userId = userId!!,
        username = username!!,
        title = title!!,
        learning = learning!!,
        relatedStory = relatedStory!!,
        createdOn = createdOn,
        categoryId = categoryId!!,
        likes = likes ?: emptyList(),
        comments = comments ?: emptyList()
    )
}

fun toPersonalLifeLesson(p : PersonalLifeLessonResponse) = PersonalLifeLesson(
    _id = p._id!!,
    userId = p.userId!!,
    username = p.username!!,
    title = p.title!!,
    learning = p.learning!!,
    relatedStory = p.relatedStory!!,
    createdOn = p.createdOn,
    categoryId = p.categoryId!!,
    likes = p.likes ?: emptyList(),
    comments = p.comments ?: emptyList()
)