package com.android.personallifelessons.presenter.dashboard

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.personallifelessons.presenter.components.DashboardIconActions
import com.android.personallifelessons.presenter.navgraph.Destinations
import com.google.gson.Gson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

@Composable
fun PersonalLifeLessonLazyColumn(
    viewModel: DashboardViewModel,
    navController: NavController
){
    LazyColumn{
        items(viewModel.personalLifeLessonList.value){ pll ->
            PersonalLifeLessonCard(
                modifier = Modifier.padding(10.dp),
                pll = pll,
                userId = viewModel.userId
            ){ action ->
                when(action){
                    DashboardIconActions.LIKE -> {
                        viewModel.likePersonalLifeLesson(pll._id, viewModel.userId)
                    }
                    DashboardIconActions.COMMENT -> {
                        val pllJson = Gson().toJson(pll)
                        val pllUri = Uri.encode(pllJson)
                        navController.navigate(Destinations.COMMENT_SCREEN.route+"/$pllUri")
                    }
                    DashboardIconActions.SHARE -> {
                        //TODO Share implementation
                    }
                    DashboardIconActions.DELETE -> {
                        viewModel.deletePersonalLifeLesson(pll._id)
                    }
                    DashboardIconActions.SAVE -> {
                        viewModel.cachePersonalLifeLesson(pll)
                    }
                }
            }
        }
    }
}