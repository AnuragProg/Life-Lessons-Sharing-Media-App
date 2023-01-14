package com.android.personallifelessons

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.room.LikedDislikedDao
import com.android.personallifelessons.presenter.auth.LoginScreen
import com.android.personallifelessons.presenter.auth.SignUpScreen
import com.android.personallifelessons.ui.theme.PersonalLifeLessonsTheme
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthActivity : ComponentActivity(), KoinComponent {
    private val likedDislikedDao: LikedDislikedDao by inject()
    private val userDatastore: UserDatastore by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clearEverything()
        setContent {
            PersonalLifeLessonsTheme {
                AuthNavigationGraph{
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    // Doing clean up tasks
    // clearing all the tokens and userid's
    // liking the posts
    private fun clearEverything(){
        lifecycleScope.launch{userDatastore.clear()}
        lifecycleScope.launch{likedDislikedDao.clearLikedDislikedPlls()}
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