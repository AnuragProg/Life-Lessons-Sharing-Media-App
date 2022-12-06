package com.android.personallifelessons.data.dto.request

data class PllRequest(
    val title: String,
    val learning: String,
    val relatedStory: String,
    val categoryId: String,
)

data class PllUpdateRequest(
    val _id: String,
    val title: String,
    val learning: String,
    val relatedStory: String,
    val categoryId: String
)