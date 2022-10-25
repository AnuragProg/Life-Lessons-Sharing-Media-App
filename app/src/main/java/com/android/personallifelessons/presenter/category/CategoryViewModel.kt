package com.android.personallifelessons.presenter.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.Category
import com.android.personallifelessons.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Outcome<String>>(Outcome.Loading())
    val uiState get() = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories get() = _categories.asStateFlow()

    init{ refresh() }

    fun refresh() = viewModelScope.launch{
        when(val result = categoryRepository.getAllCategories().first()){
            is Outcome.Error -> _uiState.value = Outcome.Error(result.message!!)
            is Outcome.Loading -> _uiState.value = Outcome.Loading()
            is Outcome.Success -> {
                _categories.value = result.data!!
                _uiState.value = Outcome.Success("Categories loaded")
            }
        }
    }


}
