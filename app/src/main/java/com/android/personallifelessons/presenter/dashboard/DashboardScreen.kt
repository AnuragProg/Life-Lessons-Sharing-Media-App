package com.android.personallifelessons.presenter.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.android.personallifelessons.components.Outcome
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DashboardScreen(
    userId: String, navController: NavController,
) {
    val viewModel = getViewModel<DashboardViewModel>{ parametersOf(userId) }
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when (uiState) {
            is Outcome.Error -> Toast.makeText(context, uiState.message!!, Toast.LENGTH_SHORT)
                .show()
            is Outcome.Loading -> CircularProgressIndicator()
            is Outcome.Success -> {
                Toast.makeText(context, uiState.data, Toast.LENGTH_SHORT).show()
                PersonalLifeLessonLazyColumn(
                    viewModel, navController
                )
            }
        }
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PersonalLifeLessonLazyColumnPreview(){
    DashboardScreen("", getViewModel{ parametersOf("") })
}