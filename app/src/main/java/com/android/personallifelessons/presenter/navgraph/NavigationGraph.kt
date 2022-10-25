package com.android.personallifelessons.presenter.navgraph

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.personallifelessons.domain.model.PersonalLifeLesson
import com.android.personallifelessons.domain.model.User
import com.android.personallifelessons.presenter.category.CategoryScreen
import com.android.personallifelessons.presenter.comments.CommentScreen
import com.android.personallifelessons.presenter.dashboard.DashboardScreen
import com.android.personallifelessons.presenter.post.PostScreen
import com.android.personallifelessons.presenter.profile.ProfileScreen
import com.android.personallifelessons.presenter.saved.SavedScreen
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@Composable
fun NavigationGraph(
    user: User
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destinations.DASHBOARD_SCREEN.route,
    ){

        composable(Destinations.DASHBOARD_SCREEN.route){
            DashboardScreen(userId = user._id, navController = navController)
        }

        composable(
            route = Destinations.COMMENT_SCREEN.route+"/{pll}",
            arguments = listOf(
                navArgument("pll"){type=PersonalLifeLessonNavType()}
            )
        ){
            val pll = it.arguments?.getParcelable<PersonalLifeLesson>("pll")
            if(pll==null){
                Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Corrupted data") }
            }else{
                CommentScreen(commentIds = pll.comments, pll = pll)
            }
        }

        composable(Destinations.POST_SCREEN.route){
            PostScreen()
        }

        composable(Destinations.SAVED_SCREEN.route){
            SavedScreen()
        }

        composable(Destinations.CATEGORY_SCREEN.route){
            CategoryScreen()
        }

        composable(
            route=Destinations.PROFILE_SCREEN.route + "/{userId}",
            arguments = listOf(
                navArgument("userId"){ type=NavType.StringType }
            )
        ){
            val userId = it.arguments?.getString("userId")
            ProfileScreen(userId = userId!!)
        }
    }
}