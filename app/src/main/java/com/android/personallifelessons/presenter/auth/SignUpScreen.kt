package com.android.personallifelessons.presenter.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.*
import com.android.personallifelessons.R
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.presenter.shared.LoadingPage
import es.dmoral.toasty.Toasty
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel(),
    moveToDashboardScreen: ()->Unit,
    moveToSignInScreen: ()->Unit
){

    val context = LocalContext.current
    val username by viewModel.username.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var isLoading by remember{mutableStateOf(false)}

    LaunchedEffect(uiState){
        when(val state = uiState){
            is Outcome.Error -> {
                Toasty.error(context, state.error.message!!).show()
                isLoading = false
            }
            Outcome.Loading -> isLoading = true
            is Outcome.Success -> {
                moveToDashboardScreen()
                isLoading = false
            }
            null -> {}
        }
    }

    Box(
        modifier= Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        // Showing loading page
        if(isLoading){
            LoadingPage()
        }else{
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                // Lottie animations
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.secure_login))
                val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
                LottieAnimation(modifier=Modifier.size(200.dp),composition = composition, progress = { progress })
                Column{
                    OutlinedTextField(
                        value = username, onValueChange = viewModel::setUsername,
                        label = {
                            Text(text = "Username")
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(Modifier.height(20.dp))
                    OutlinedTextField(
                        value = email, onValueChange = viewModel::setEmail, keyboardOptions= KeyboardOptions(keyboardType= KeyboardType.Email),
                        label = {
                            Text(text = "Email")
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(Modifier.height(20.dp))
                    OutlinedTextField(
                        value = password, onValueChange = viewModel::setPassword, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        label = {
                            Text(text = "Password")
                        },
                        shape = RoundedCornerShape(10.dp),
                        visualTransformation = PasswordVisualTransformation()
                    )
                }

                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    Icon(
                        imageVector = Icons.Filled.NavigateNext, contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, if (isLoading) Color.Gray else Color.Black, CircleShape)
                            .clickable(enabled = !isLoading) {viewModel.signUp()},
                        tint = if(isLoading) Color.Gray else Color.Black
                    )
                }
            }

            Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
                Row{
                    Text(text="Already have an account. ")
                    Text(modifier=Modifier.clickable{moveToSignInScreen()},text="Sign In", fontWeight = FontWeight.Bold, color = Color.Blue)
                }
            }
        }
    }
}


@Preview(showBackground=true)
@Composable
fun SignUpScreenPreview(){
    SignUpScreen(moveToDashboardScreen = {}){}
}
