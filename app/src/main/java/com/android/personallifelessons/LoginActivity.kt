package com.android.personallifelessons

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.personallifelessons.presenter.login.LoginScreen
import com.android.personallifelessons.ui.theme.PersonalLifeLessonsTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalLifeLessonsTheme {
                LoginScreen() {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
