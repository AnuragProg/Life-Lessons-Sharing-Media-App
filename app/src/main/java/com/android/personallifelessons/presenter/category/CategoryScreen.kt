package com.android.personallifelessons.presenter.category

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.personallifelessons.components.Outcome
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CategoryScreen() {
    val viewModel = getViewModel<CategoryViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when(uiState){
            is Outcome.Error -> Toast.makeText(context, viewModel.uiState.value.message, Toast.LENGTH_SHORT).show()
            is Outcome.Loading -> CircularProgressIndicator()
            is Outcome.Success -> {
                CategoryColumn(categories = viewModel.categories.value)
                Toast.makeText(context, viewModel.uiState.value.data, Toast.LENGTH_SHORT).show()
            }
        }
    }
}