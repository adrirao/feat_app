package com.unlam.feat.common

sealed class Screen(
    val route: String
) {
    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    object Register :Screen("register_screen")

    object Profile : Screen("profile_screen")
    object Events : Screen("events_screen")
    object Home : Screen("home_screen")
    object SearchList : Screen("search_list_screen")
    object Search : Screen("search_screen")
    object Invite : Screen("invite_screen")

    object AddEvent: Screen("event_add_screen")

    object ConfigProfile: Screen("config_profile_screen")
}