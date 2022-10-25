package com.android.personallifelessons.presenter.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.PersonalLifeLessonRoomDto
import com.android.personallifelessons.domain.room.SavedItemDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavedViewModel(
    private val savedItemDao: SavedItemDao
): ViewModel() {

    private val _personalLifeLessons = MutableStateFlow<List<PersonalLifeLessonRoomDto>>(emptyList())
    val personalLifeLessons get() = _personalLifeLessons.asStateFlow()

    private val _uiState = MutableStateFlow<Outcome<String>>(Outcome.Loading())
    val uiState get() = _uiState.asStateFlow()

    init{
        viewModelScope.launch{
            savedItemDao.getCachedPersonalLifeLessons().collectLatest{
                if(it.isEmpty()) _uiState.value = Outcome.Error("Empty Cache")
                else {
                    _personalLifeLessons.value = it
                    _uiState.value = Outcome.Success("Successfully loaded Cache")
                }
            }
        }
    }
}