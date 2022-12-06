package com.android.personallifelessons.data.dto.response

data class PllResponse(
    val _id: String,
    val categoryId: String,
    val createdOn: Long,
    val learning: String,
    val relatedStory: String,
    val title: String,
    val userId: String,
    val username: String,
    val likes: List<String>?
)

data class Pll(
    val _id: String,
    val categoryId: String,
    val createdOn: Long,
    val learning: String,
    val relatedStory: String,
    val title: String,
    val userId: String,
    val username: String,
    val likes: List<String>?,
    val isLiked: Boolean
)

// For checking if user previously liked this post
fun PllResponse.toPll(userId: String): Pll {
    return Pll(
        _id = _id,
        categoryId = categoryId,
        createdOn = createdOn,
        learning = learning,
        relatedStory = relatedStory,
        title = title,
        userId = userId,
        username = username,
        likes = likes,
        isLiked = if(likes!=null)userId in likes else false
    )
}


// For hashmap pllid to pll
fun List<Pll>.toHashMap(): HashMap<String, Pll>{
    val map = HashMap<String, Pll>()
    forEach{ pll ->
        map[pll._id] = pll
    }
    return map
}
