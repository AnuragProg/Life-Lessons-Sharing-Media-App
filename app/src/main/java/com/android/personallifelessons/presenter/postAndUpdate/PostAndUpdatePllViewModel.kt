package com.android.personallifelessons.presenter.postAndUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.request.PllRequest
import com.android.personallifelessons.data.dto.request.PllUpdateRequest
import com.android.personallifelessons.data.dto.response.CategoryResponse
import com.android.personallifelessons.data.dto.response.PllResponse
import com.android.personallifelessons.domain.repository.CategoryRepository
import com.android.personallifelessons.domain.repository.PllRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class PostAndUpdatePllViewModel(
    private val pllRepo: PllRepository,
    private val categoryRepo: CategoryRepository,
    private val previousPll: PllResponse?
): ViewModel(){

    private val _uiState = MutableStateFlow<Outcome<String>?>(null)
    val uiState get() = _uiState.asStateFlow()

    private val _title = MutableStateFlow(previousPll?.title ?: "")
    val title get() = _title.asStateFlow()
    private val _learning = MutableStateFlow(previousPll?.learning ?: "")
    val learning get() = _learning.asStateFlow()
    private val _relatedStory = MutableStateFlow(previousPll?.relatedStory ?: "")
    val relatedStory get() = _relatedStory.asStateFlow()
    private val _category = MutableStateFlow<CategoryResponse?>(null)
    val category get() = _category.asStateFlow()

    private val _categories = MutableStateFlow<List<CategoryResponse>>(emptyList())
    val categories get() = _categories.asStateFlow()

    init{
        viewModelScope.launch{
            when(val response = categoryRepo.getCategories()){
                is Outcome.Error -> {
                    _uiState.value = Outcome.Error(response.error)
                }
                Outcome.Loading -> {}
                is Outcome.Success -> {
                    _categories.value = response.data
                }
            }
        }
    }

    fun postOrUpdate(){
        if(category.value == null){
            _uiState.value = Outcome.Error(Exception("Select category"))
            return
        }
        if(title.value.isBlank() || learning.value.isBlank() || relatedStory.value.isBlank()){
            _uiState.value = Outcome.Error(Exception("No fields should be empty"))
            return
        }
        if(previousPll!=null)
            updatePll(previousPll)
        else
            postPll()
    }




    fun setTitle(title:String) { _title.value = title }
    fun setLearning(learning:String) { _learning.value = learning}
    fun setCategory(category:CategoryResponse) { _category.value = category}
    fun setRelatedStory(relatedStory: String){ _relatedStory.value = relatedStory}

    private fun postPll(){
        viewModelScope.launch{
            _uiState.value = Outcome.Loading
            val pll = PllRequest(
                title = title.value, learning = learning.value, relatedStory = relatedStory.value, categoryId = category.value!!._id
            )
            _uiState.value = pllRepo.postPll(pll)
        }
    }

    private fun updatePll(pll: PllResponse){
        viewModelScope.launch{
            val pllUpdate = PllUpdateRequest(
                _id = pll._id, title = title.value, learning = learning.value, relatedStory = relatedStory.value, categoryId = category.value!!._id
            )
            _uiState.value = pllRepo.updatePll(pllUpdate)
        }
    }
}