package com.android.personallifelessons.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey



// For storing liked and disliked posts from user
@Entity
data class LikedDislikedPll(
    @PrimaryKey(autoGenerate = false)
    val pllId: String,
    val liked: Boolean
)


fun List<LikedDislikedPll>.toHashMap(): HashMap<String, Boolean>{
    val map = HashMap<String,Boolean>()
    forEach{ likedDislikedPll ->
        map[likedDislikedPll.pllId] = likedDislikedPll.liked
    }
    return map
}