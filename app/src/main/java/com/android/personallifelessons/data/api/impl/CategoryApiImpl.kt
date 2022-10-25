package com.android.personallifelessons.data.api.impl

import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.api.CategoryApi
import com.android.personallifelessons.data.api.genericops.genericListValueEventListenerFlow
import com.android.personallifelessons.data.api.genericops.genericValueEventListenerFlow
import com.android.personallifelessons.data.components.DBPaths
import com.android.personallifelessons.data.dto.response.CategoryResponse
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow

class CategoryApiImpl(
    db: DatabaseReference
): CategoryApi {

    private val pathReference = db.child(DBPaths.CATEGORIES)
    override suspend fun getCategory(categoryId: String): Flow<Outcome<CategoryResponse>> {
        return genericValueEventListenerFlow(pathReference.child(categoryId))
    }

    override suspend fun getAllCategories(): Flow<Outcome<List<CategoryResponse>>> {
        return genericListValueEventListenerFlow(pathReference)
    }
}