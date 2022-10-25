package com.android.personallifelessons

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.domain.model.User
import com.android.personallifelessons.presenter.navgraph.NavigationGraph
import com.android.personallifelessons.presenter.post.PostScreen
import com.android.personallifelessons.ui.theme.PersonalLifeLessonsTheme
import com.google.gson.Gson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = User(
            _id = "user1",
            email = "somemail@gmail.com",
            joinedOn = 302902930293,
            photo = "https:cloud.com",
            username = "username1"
        )

        setContent {
            PersonalLifeLessonsTheme {
                NavigationGraph(user = user)
            }
        }


    }
}
