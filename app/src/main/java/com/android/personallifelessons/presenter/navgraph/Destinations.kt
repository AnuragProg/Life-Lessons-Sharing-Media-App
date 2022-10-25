package com.android.personallifelessons.presenter.navgraph

enum class Destinations(
    val route: String,
) {
    COMMENT_SCREEN(route ="CommentScreen"),
    PROFILE_SCREEN(route="ProfileScreen"),
    POST_SCREEN(route="PostScreen"),
    CATEGORY_SCREEN(route="CategoryScreen"),
    DASHBOARD_SCREEN(route="DashboardScreen"),
    SAVED_SCREEN(route="SavedScreen")
}