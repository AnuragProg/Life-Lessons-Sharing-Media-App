package com.android.personallifelessons.presenter.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.ui.graphics.vector.ImageVector


enum class Destinations(
    val route:String, val onBottomNav: Boolean,
    val icon: ImageVector?, val label: String
){
    DASHBOARD("dashboard", true, Icons.Filled.Dashboard, "Dashboard"),
    CATEGORY("category", true, Icons.Filled.Category, "Categories"),
    COMMENT("comment", false, null, "Comments"),
    POSTANDUPDATE("postAndUpdate", false, null, "Post or Update"),


    // Login
    LOGIN("login", false, null, "Login"),
    LOGINWITHPASSWORD("loginWithPassword", false, null, "Login With Password"),
    LOGINSUCCESSFUL("loginSuccessful", false, null, "Login Successful");

    companion object{
        fun findDestination(route: String): Destinations{
            values().forEach{ dest ->
                if(dest.route in route) return dest
            }
            throw Exception("Destination not found")
        }
    }
}