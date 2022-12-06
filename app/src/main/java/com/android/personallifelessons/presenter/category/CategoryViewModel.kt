package com.android.personallifelessons.presenter.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.data.dto.response.CategoryResponse
import com.android.personallifelessons.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepo: CategoryRepository,
) : ViewModel(){

    private val _uiState = MutableStateFlow<Outcome<List<CategoryResponse>>>(Outcome.Loading)
    val uiState get() = _uiState.asStateFlow()

    init{
        viewModelScope.launch{
            _uiState.value = categoryRepo.getCategories()
        }
    }
}