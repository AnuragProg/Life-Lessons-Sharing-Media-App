package com.android.personallifelessons.domain.room

import androidx.room.*
import com.android.personallifelessons.domain.models.LikedDislikedPll
import kotlinx.coroutines.flow.Flow


@Dao
interface LikedDislikedDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLikedDislikedPll(likeDislikePll: LikedDislikedPll)

    @Transaction
    @Query("select * from likeddislikedpll")
    fun getLikedDislikedPlls(): Flow<List<LikedDislikedPll>>


    @Transaction
    @Query("delete from likeddislikedpll")
    suspend fun clearLikedDislikedPlls()

}