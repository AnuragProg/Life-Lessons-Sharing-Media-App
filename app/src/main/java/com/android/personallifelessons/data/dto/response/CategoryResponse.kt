package com.android.personallifelessons.data.dto.response

import com.android.personallifelessons.domain.model.Category
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class CategoryResponse(
    val _id: String? = null,
    val categoryName: String? = null,
    val categoryDescription: String? = null
){
    @Exclude
    fun toCategory(): Category = Category(
        _id = _id!!,
        categoryName = categoryName!!,
        categoryDescription = categoryDescription!!
    )
}

fun toCategory(category: CategoryResponse): Category
= Category(
    _id = category._id!!,
    categoryName = category.categoryName!!,
    categoryDescription = category.categoryDescription!!
)