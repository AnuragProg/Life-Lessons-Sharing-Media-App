package com.android.personallifelessons.presenter.dashboard

import android.util.Log
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

    init{

        loadPlls()

        // Retrieving all saved pllids with property is liked to check whether given pll is liked or not
        viewModelScope.launch{
            likedDislikedDao.getLikedDislikedPlls().map{ it.toHashMap() }.collectLatest{
                likedDislikedPlls = it
            }
        }
    }

    private fun loadPlls(){
        viewModelScope.launch{
            _uiState.value = Outcome.Loading
            when(val response = pllRepo.getPlls()){
                is Outcome.Error -> _uiState.value = Outcome.Error(response.error)
                Outcome.Loading -> _uiState.value = Outcome.Loading
                is Outcome.Success -> {
                    _plls.value = response.data
                    _uiState.value = Outcome.Success("Successfully loaded posts")
                }
            }
        }
    }

    fun isPostInCache(pll: Pll): Boolean = likedDislikedPlls.contains(pll._id)

    private fun isPostLikedInCache(pll: Pll) :Boolean = likedDislikedPlls[pll._id]?:false

    /**
     * Check whether there is record in cache
     * if true:
     *      @return status of the post (liked or not liked)
     * Check whether the post is liked in remote server
     */
    fun isPostLiked(pll: Pll): Boolean{
        if(isPostInCache(pll)){
            Log.d("post", "Post is in cache and is ${isPostLikedInCache(pll)}")
            return isPostLikedInCache(pll)
        }
        Log.d("post", "Post is ${pll.isPostLikedOnServer()} = liked on server")
        return pll.isPostLikedOnServer()
    }

    /**
     * Stores liked post id with liked property locally for updating the server in future
     */
    fun likePost(pll: Pll){
        viewModelScope.launch{
            likedDislikedDao.saveLikedDislikedPll(LikedDislikedPll(pll._id, true))
        }
    }

    /**
     * Stores liked post id with disliked property locally for updating the server in future
     */
    fun dislikePost(pll: Pll){
        viewModelScope.launch{
            likedDislikedDao.saveLikedDislikedPll(LikedDislikedPll(pll._id, false))
        }
    }

    /**
     * Deletes the post and updates the pll posts
     */
    fun deletePost(pllId: String){
        viewModelScope.launch{
            // Delete the pll
            pllRepo.deletePll(pllId)

            // Update with new pll
            loadPlls()
        }

    }
}