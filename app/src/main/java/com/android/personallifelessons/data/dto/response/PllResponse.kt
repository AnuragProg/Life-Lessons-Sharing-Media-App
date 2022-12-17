package com.android.personallifelessons.data.dto.response

data class PllResponse(
    val _id: String,
    val categoryId: String,
    val createdOn: String,
    val learning: String,
    val relatedStory: String,
    val title: String,
    val userId: String,
    val username: String,
    val likes: List<String>?,
    val comments: List<String>
)

data class Pll(
    val _id: String,
    val categoryId: String,
    val createdOn: String,
    val learning: String,
    val relatedStory: String,
    val title: String,
    val userId: String,
    val username: String,
    val likes: List<String>?,
    val isLiked: Boolean,
    val isOwner: Boolean,
    val comments: List<String>?
)

// For checking if user previously liked this post
fun PllResponse.toPll(uId: String): Pll {
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
        isLiked = if(likes!=null)userId in likes else false,
        isOwner = uId == userId,
        comments = comments
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
