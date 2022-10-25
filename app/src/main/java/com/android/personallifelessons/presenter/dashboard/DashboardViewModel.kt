package com.android.personallifelessons.presenter.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.domain.model.toPersonalLifeLessonRoomDto
import com.android.personallifelessons.domain.repository.DashBoardRepository
import com.android.personallifelessons.domain.room.SavedItemDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val dashBoardRepository: DashBoardRepository,
    private val savedItemDao: SavedItemDao,
    val userId: String,
) : ViewModel() {

    private val _personalLifeLessonList = MutableStateFlow<List<PersonalLifeLesson>>(emptyList())
    val personalLifeLessonList get() = _personalLifeLessonList.asStateFlow()

    private val _uiState = MutableStateFlow<Outcome<String>>(Outcome.Loading())
    val uiState get() = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            when (val result = dashBoardRepository.getPersonalLifeLessons().first()) {
                is Outcome.Error -> {
                    _uiState.value = Outcome.Error(result.message!!)
                }
                is Outcome.Loading -> {
                    _uiState.value = Outcome.Loading()
                }
                is Outcome.Success -> {
                    _uiState.value = Outcome.Success("Feed successfully loaded")
                    _personalLifeLessonList.value = result.data!!
                }
            }
        }
    }

    fun likePersonalLifeLesson(
        pid: String, userId: String,
    ) =
        viewModelScope.launch {
            when(val result = dashBoardRepository.likePersonalLifeLesson(pid = pid, userId = userId).first()){
                is Outcome.Error,is Outcome.Loading -> _uiState.value = result
                is Outcome.Success -> _uiState.value = Outcome.Success("Post liked")
            }
        }

    fun dislikePersonalLifeLesson(
        pid: String, userId: String,
    ) = viewModelScope.launch {
        when (val result = dashBoardRepository.dislikePersonalLifeLesson(pid = pid, userId = userId).first()) {
            is Outcome.Error,is Outcome.Loading -> _uiState.value = result
            is Outcome.Success -> _uiState.value = Outcome.Success("Post disliked")
        }
    }

    fun deletePersonalLifeLesson(
        pid: String,
    ) = viewModelScope.launch {
        when (val result = dashBoardRepository.deletePersonalLifeLesson(pid = pid).first()) {
            is Outcome.Error, is Outcome.Loading -> _uiState.value = result
            is Outcome.Success -> _uiState.value = Outcome.Success("Successfully deleted")
        }
    }

    fun cachePersonalLifeLesson(pll: PersonalLifeLesson) = viewModelScope.launch{
        _uiState.value = Outcome.Loading()
        savedItemDao.insertPersonalLifeLesson(pll.toPersonalLifeLessonRoomDto())
        _uiState.value = Outcome.Success("Saved ${pll.title}")
    }

}