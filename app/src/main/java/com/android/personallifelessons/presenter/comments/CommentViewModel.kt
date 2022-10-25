package com.android.personallifelessons.presenter.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.Comment
import com.android.personallifelessons.domain.repository.CommentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CommentViewModel(
    private val commentRepository: CommentRepository,
    private val commentIds: List<String>
): ViewModel() {

    private val _uiState = MutableStateFlow<Outcome<String>>(Outcome.Loading())
    val uiState get() = _uiState.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments get() = _comments.asStateFlow()

    init { refresh() }

    fun refresh()
    = viewModelScope.launch{
        when(val result = commentRepository.getComments(commentIds).first()){
            is Outcome.Error -> _uiState.value = Outcome.Error(result.message!!)
            is Outcome.Loading -> _uiState.value = Outcome.Loading()
            is Outcome.Success -> {
                _comments.value = result.data!!
                _uiState.value = Outcome.Success("Comments loaded")
            }
        }
    }
}