package com.android.personallifelessons

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.airbnb.lottie.compose.*
import com.android.personallifelessons.components.Outcome
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.UserRepository
import com.android.personallifelessons.ui.theme.PersonalLifeLessonsTheme
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {

    private val userDatastore : UserDatastore by inject()
    private val userRepo : UserRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PersonalLifeLessonsTheme {
                val authActivityIntent = Intent(this, AuthActivity::class.java)
                val dashboardActivityIntent = Intent(this, MainActivity::class.java)
                Splash(
                    userDatastore = userDatastore,
                    userRepo = userRepo,
                    navigateToLoginActivity = {
                        startActivity(authActivityIntent)
                        finish()
                    },
                    navigateToDashboardActivity = {
                        startActivity(dashboardActivityIntent)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun Splash(
    userDatastore: UserDatastore,
    userRepo: UserRepository,
    navigateToLoginActivity: () -> Unit,
    navigateToDashboardActivity: () -> Unit
){

    val context = LocalContext.current
    LaunchedEffect(Unit){
        val token = userDatastore.getToken().first()
        val userId = userDatastore.getUserId().first()
        Log.d("token", "datastore has token=$token && userId=$userId")
        if(token!=null && userId!=null){
            // login using token
            val result = userRepo.signInWithToken()
            if(result is Outcome.Error){
                Toasty.error(context, result.error.message!!).show()
                navigateToLoginActivity()
                return@LaunchedEffect
            }
            navigateToDashboardActivity()
        }else
            navigateToLoginActivity()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.social_media))
        val progress by animateLottieCompositionAsState(composition = composition, iterations = LottieConstants.IterateForever)
        LottieAnimation(
            composition = composition,
            progress = {progress}
        )
    }
}


//@Preview
//@Composable
//fun SplashReview(){
//    Splash()
//}