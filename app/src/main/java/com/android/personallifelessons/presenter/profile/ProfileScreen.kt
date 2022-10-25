package com.android.personallifelessons.presenter.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.personallifelessons.components.Outcome
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProfileScreen(
    userId: String
) {

    val viewModel = getViewModel<ProfileViewModel>{parametersOf(userId)}
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        when(uiState){
            is Outcome.Error -> Toast.makeText(context, uiState.message, Toast.LENGTH_SHORT).show()
            is Outcome.Loading -> CircularProgressIndicator()
            is Outcome.Success -> {
                ProfileScreenContent(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProfileScreenContent(viewModel: ProfileViewModel) {

    val photoUrl by viewModel.photoUrl.collectAsStateWithLifecycle()
    val username by viewModel.username.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            value = photoUrl ?: "Unknown",
            onValueChange = viewModel::setPhotoUrl
        )
        OutlinedTextField(
            value = username,
            onValueChange = viewModel::setUsername
        )
        OutlinedTextField(
            value = email,
            onValueChange = viewModel::setEmail
        )
    }

}