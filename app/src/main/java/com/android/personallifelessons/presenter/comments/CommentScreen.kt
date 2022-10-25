package com.android.personallifelessons.presenter.comments

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CommentScreen(
    commentIds: List<String>, pll: PersonalLifeLesson
) {

    val viewModel = getViewModel<CommentViewModel>{ parametersOf(commentIds) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (uiState) {
        is Outcome.Error -> Toast.makeText(context, uiState.message!!, Toast.LENGTH_SHORT)
            .show()
        is Outcome.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is Outcome.Success -> CommentColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            personalLifeLesson = pll,
            comments = viewModel.comments.value
        )
    }
}