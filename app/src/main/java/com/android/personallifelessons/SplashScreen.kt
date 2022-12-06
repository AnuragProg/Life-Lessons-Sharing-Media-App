package com.android.personallifelessons

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.*
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.ui.theme.PersonalLifeLessonsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashScreen : ComponentActivity() {

    private val userDatastore : UserDatastore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PersonalLifeLessonsTheme {
                val loginActivityIntent = Intent(this, LoginActivity::class.java)
                val dashboardActivityIntent = Intent(this, MainActivity::class.java)
                Splash(
                    userDatastore = userDatastore,
                    navigateToLoginActivity = {
                        startActivity(loginActivityIntent)
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
    navigateToLoginActivity: () -> Unit,
    navigateToDashboardActivity: () -> Unit
){

    LaunchedEffect(Unit){
        val token = userDatastore.getToken().first()
        val userId = userDatastore.getUserId().first()
        delay(5000)
        if(token==null || userId==null)
            navigateToLoginActivity()
        else
            navigateToDashboardActivity()
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