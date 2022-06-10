package com.unlam.feat.common

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>? = null
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

    object SearchEventDetail : Screen("search_event_detail", listOf(
        navArgument("idEvent") { type = NavType.StringType }
    ))
}