package com.android.personallifelessons.presenter.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToHomeScreen
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.collections.Map.Entry


enum class Destinations(
    val route:String, val onBottomNav: Boolean,
    val icon: ImageVector?, val label: String
){
    DASHBOARD("dashboard", true, Icons.Filled.Dashboard, "Dashboard"),
    POSTANDUPDATE("postAndUpdate", true, Icons.Filled.PostAdd, "Post"),
    CATEGORY("category", true, Icons.Filled.Category, "Categories"),
    COMMENT("commentText", false, null, "Comments"),


    // Login
    LOGIN("login", false, null, "Login"),
    LOGINWITHPASSWORD("loginWithPassword", false, null, "Login With Password"),
    LOGINSUCCESSFUL("loginSuccessful", false, null, "Login Successful");

    companion object{

        private val destinations = hashMapOf(*(values().map{Pair(it.route, it)}.toTypedArray()))
        fun findDestination(route: String): Destinations{
            Log.d("destinations", "Requested route is $route")
            val parsedRoute = route.split("?")[0].split("/")[0]
            return destinations[parsedRoute] ?: throw Exception("Destination not found")
        }
    }
}