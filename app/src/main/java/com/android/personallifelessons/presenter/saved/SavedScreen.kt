package com.android.personallifelessons.presenter.saved

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.PersonalLifeLessonRoomDto
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SavedScreen() {

    val viewModel = getViewModel<SavedViewModel>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when(uiState){
            is Outcome.Error -> Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            is Outcome.Loading -> CircularProgressIndicator()
            is Outcome.Success -> {
                Toast.makeText(context, uiState.data, Toast.LENGTH_SHORT).show()
                SavedScreenContent(plls = viewModel.personalLifeLessons.value)
            }
        }
    }
}

@Composable
fun SavedScreenContent(
    modifier : Modifier = Modifier,
    plls : List<PersonalLifeLessonRoomDto>
) {
    LazyColumn(modifier=modifier){
        items(plls){ item: PersonalLifeLessonRoomDto ->
            SavedPersonalLifeLessonCard(pll = item)
        }
    }
}