package com.android.personallifelessons.presenter.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.CommentRequest
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.domain.models.LikedDislikedPll
import com.android.personallifelessons.domain.models.toHashMap
import com.android.personallifelessons.domain.repository.CommentRepository
import com.android.personallifelessons.domain.repository.PllRepository
import com.android.personallifelessons.domain.room.LikedDislikedDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class CommentViewModel(
    private val commentRepo: CommentRepository,
    private val pllRepo: PllRepository,
    private val likedDislikedDao: LikedDislikedDao,
    private val initialPll: Pll,
) : ViewModel(){

    private val _uiState = MutableStateFlow<Outcome<String>>(Outcome.Loading)
    val uiState get() = _uiState.asStateFlow()

    private val _commentsList = MutableStateFlow<List<CommentResponse>>(emptyList())
    val commentsList get() = _commentsList.asStateFlow()

    private val _commentText = MutableStateFlow("")
    val commentText get() = _commentText.asStateFlow()

    // When user posts commentText, the badge needs to be updated
    // we will update the pll as well
    private val _currentPll = MutableStateFlow(initialPll)
    val currentPll get() = _currentPll.asStateFlow()

    private var _likedDislikedPlls = HashMap<String, Boolean>()

    init{
        loadComments()

        // retrieving updated pll for initial pll card
        viewModelScope.launch{pllRepo.getPll(currentPll.value._id).also{if(it is Outcome.Success) _currentPll.value = it.data }}

        viewModelScope.launch{
            likedDislikedDao.getLikedDislikedPlls().map{it.toHashMap()}.collectLatest {
                _likedDislikedPlls = it
            }
        }

    }

    fun setComment(comment: String) { _commentText.value = comment}

    fun likePost(){
        viewModelScope.launch{likedDislikedDao.saveLikedDislikedPll(likeDislikePll = LikedDislikedPll(currentPll.value._id, true))}
    }
    fun dislikePost(){
        viewModelScope.launch{likedDislikedDao.saveLikedDislikedPll(likeDislikePll = LikedDislikedPll(currentPll.value._id, false))}
    }

    /**
     * @return Pair( isThisValueCachedOne, isLiked )
     */
    fun isLiked(): Pair<Boolean, Boolean>{
        if(_likedDislikedPlls.containsKey(currentPll.value._id)){
            return Pair(true, _likedDislikedPlls[currentPll.value._id]!!)
        }
        return Pair(false, currentPll.value.isLiked)
    }

    private fun loadComments(){
        viewModelScope.launch {
            _uiState.value = Outcome.Loading
            when(val response = commentRepo.getComments(initialPll._id)){
                is Outcome.Success -> {
                    _commentsList.value = response.data
                    _uiState.value = Outcome.Success("Loaded comments")
                }
                is Outcome.Error -> _uiState.value = response
                Outcome.Loading -> _uiState.value = Outcome.Loading
            }

        }
    }

    fun postComment(){
        viewModelScope.launch{
            _uiState.value = Outcome.Loading
            val comment= CommentRequest(
                pllId = initialPll._id,
                comment = commentText.value,
            )


            // post commentText to server
            when(val response = commentRepo.addComment(comment)){
                is Outcome.Success ->{}
                is Outcome.Error ->{
                    _uiState.value = response
                    return@launch
                }
                Outcome.Loading -> {}
            }

            // get updated pll
            when(val r = pllRepo.getPll(currentPll.value._id)){
                is Outcome.Error ->{
                    // in case of getting updated pll
                    // inform user
                    _uiState.value = r
                    return@launch
                }
                // in case of loading
                Outcome.Loading -> {}

                // in case of success
                is Outcome.Success -> {
                    _currentPll.value = r.data
                }
            }

            // posted commentText successfully
            // fetched commentText successfully
            // reset the commentText value
            _commentText.value = ""
            loadComments()
       }
    }
}