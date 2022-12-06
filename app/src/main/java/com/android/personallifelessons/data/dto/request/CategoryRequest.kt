package com.android.personallifelessons.data.dto.request

data class CategoryRequest(
    val title: String,
    val description: String,
)

data class CategoryUpdateRequest(
    val _id: String,
    val title: String,
    val description: String,
)