package com.android.personallifelessons.presenter.post

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.PersonalLifeLessonRequest
import com.android.personallifelessons.domain.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PostViewModel(
    private val postRepository: PostRepository,
    private val userId: String, private val username: String
): ViewModel() {

    /**
    data class PersonalLifeLessonRequest(
    val userId: String,
    val username: String,
    val title: String,
    val learning: String,
    val relatedStory: String,
    val createdOn: Long,
    val categoryId: String
    )
     */
    private val _title = MutableStateFlow("")
    val title get() = _title.asStateFlow()

    private val _learning = MutableStateFlow("")
    val learning get() = _learning.asStateFlow()

    private val _relatedStory = MutableStateFlow("")
    val relatedStory get() = _relatedStory.asStateFlow()

    private val _category = MutableStateFlow<Category?>(null)
    val category get() = _category.asStateFlow()

    private val _uiState = MutableStateFlow<Outcome<String>?>(null)
    val uiState get() = _uiState.asStateFlow()

    fun setTitle(title: String){ _title.value = title }
    fun setLearning(learning: String){ _learning.value = learning}
    fun setRelatedStory(relatedStory: String){ _relatedStory.value = relatedStory}
    fun setCategory(category: Category?){ _category.value = category }

    private fun postPersonalLifeLessonRepo(
        pll: PersonalLifeLessonRequest
    ) = viewModelScope.launch{
        _uiState.value = Outcome.Loading()
        when(val result = postRepository.postPersonalLifeLesson(pll).first()){
            is Outcome.Error -> _uiState.value = Outcome.Error(result.message!!)
            is Outcome.Loading -> _uiState.value = Outcome.Loading()
            is Outcome.Success -> _uiState.value = Outcome.Success("Successfully posted")
        }
    }

    fun postPersonalLifeLesson(){
        if(!isDataValid()) {
            _uiState.value = Outcome.Error("Please fill all fields!")
            return
        }
        val pll = PersonalLifeLessonRequest(
            userId = userId, username = username,
            title = title.value, learning = learning.value, relatedStory = relatedStory.value,
            createdOn = Calendar.getInstance().time.time, categoryId = category.value!!.id
        )
        postPersonalLifeLessonRepo(pll=pll)
        clearFields()
    }

    private fun isDataValid():Boolean{
        if(_title.value.isEmpty() ||
            _learning.value.isEmpty() ||
            _relatedStory.value.isEmpty() ||
            _category.value == null
        ) return false
        return true
    }

    private fun clearFields(){
        _title.value = ""
        _learning.value = ""
        _relatedStory.value = ""
        _category.value = null
    }
}