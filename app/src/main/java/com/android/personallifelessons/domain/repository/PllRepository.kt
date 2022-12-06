package com.android.personallifelessons.domain.repository

import androidx.room.Update
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.PllRequest
import com.android.personallifelessons.data.dto.request.PllUpdateRequest
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.data.dto.response.PllResponse

interface PllRepository {
    suspend fun getPlls(): Outcome<List<Pll>>
    suspend fun getPll(pllId: String): Outcome<Pll>
    suspend fun postPll(pllRequest: PllRequest): Outcome<String>
    suspend fun updatePll(pllUpdateRequest: PllUpdateRequest): Outcome<String>
    suspend fun deletePll(pllId: String): Outcome<String>
    suspend fun likePlls(pllIds: List<String>): Outcome<String>
    suspend fun dislikePlls(pllIds: List<String>): Outcome<String>
}