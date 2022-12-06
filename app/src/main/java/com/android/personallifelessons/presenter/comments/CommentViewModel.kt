package com.android.personallifelessons.presenter.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.response.CommentResponse
import com.android.personallifelessons.data.dto.response.PllResponse
import com.android.personallifelessons.domain.repository.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommentViewModel(
    private val commentRepo: CommentRepository,
    private val pll: PllResponse
) : ViewModel(){

    private val _uiState = MutableStateFlow<Outcome<List<CommentResponse>>>(Outcome.Loading)
    val uiState get() = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            _uiState.emit(Outcome.Loading)
            _uiState.emit(commentRepo.getComments(pll._id))
        }
    }
}