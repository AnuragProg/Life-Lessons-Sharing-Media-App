package com.android.personallifelessons.presenter.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.response.Pll
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.models.LikedDislikedPll
import com.android.personallifelessons.domain.models.toHashMap
import com.android.personallifelessons.domain.repository.PllRepository
import com.android.personallifelessons.domain.room.LikedDislikedDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val pllRepo: PllRepository,
    private val likedDislikedDao: LikedDislikedDao,
    private val userDatastore: UserDatastore
): ViewModel() {

    private val _uiState = MutableStateFlow<Outcome<String>>(Outcome.Loading)
    val uiState get() = _uiState.asStateFlow()

    private var likedDislikedPlls = HashMap<String, Boolean>()

    private val _plls = MutableStateFlow<List<Pll>>(emptyList())
    val plls get() = _plls.asStateFlow()

    private val _userId = MutableStateFlow("")
    val userId get() = _userId.asStateFlow()

    init{

        viewModelScope.launch{
            userDatastore.getUserId().collectLatest { if(it!=null)_userId.value = it }
        }

        viewModelScope.launch{
            when(val response = pllRepo.getPlls()){
                is Outcome.Error -> _uiState.value = Outcome.Error(response.error)
                Outcome.Loading -> _uiState.value = Outcome.Loading
                is Outcome.Success -> {
                    _plls.value = response.data
                    _uiState.value = Outcome.Success("Successfully loaded posts")
                }
            }
        }

        viewModelScope.launch{
            likedDislikedDao.getLikedDislikedPlls().map{ it.toHashMap() }.collectLatest{
                likedDislikedPlls = it
            }
        }
    }

    /**
     * First Checks in database for any manipulations done by user that are not updated on database yet
     * For e.g liking a post or disliking a post
     * If there is some value in the db then user manipulated the post and manipulation activity will be stored
     * Otherwise default post status being liked or not displayed
     */
    fun isLiked(pll: Pll): Boolean{
        // First checking for pll liked status in room db
        if(likedDislikedPlls.containsKey(pll._id))
            return likedDislikedPlls[pll._id]!!

        // Doesn't exist in local cache that means no action performed by user on this post

        // Returning default like status
        return pll.isLiked
    }

    fun likePost(pll: Pll){
        viewModelScope.launch{
            likedDislikedDao.saveLikedDislikedPll(LikedDislikedPll(pll._id, true))
        }
    }
    fun dislikePost(pll: Pll){
        viewModelScope.launch{
            likedDislikedDao.saveLikedDislikedPll(LikedDislikedPll(pll._id, false))
        }
    }


}