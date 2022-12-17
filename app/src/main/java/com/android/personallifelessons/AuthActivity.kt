package com.android.personallifelessons

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.personallifelessons.presenter.auth.LoginScreen
import com.android.personallifelessons.presenter.auth.SignUpScreen
import com.android.personallifelessons.ui.theme.PersonalLifeLessonsTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalLifeLessonsTheme {
                AuthNavigationGraph{
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

enum class Destinations(val route: String){
    SIGNUP(route="signUp"),
    SIGNIN(route="signIn")
}


@Composable
fun AuthNavigationGraph(
    moveToDashboard: ()->Unit
){
    Scaffold {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Destinations.SIGNIN.route){

            composable(route=Destinations.SIGNIN.route){
                LoginScreen(moveToDashboard = moveToDashboard, moveToSignUpScreen = { navController.navigate(Destinations.SIGNUP.route) })
            }

            composable(route=Destinations.SIGNUP.route){
                SignUpScreen(moveToDashboardScreen = moveToDashboard, moveToSignInScreen = { navController.navigate(Destinations.SIGNIN.route) })
            }
        }
    }
}