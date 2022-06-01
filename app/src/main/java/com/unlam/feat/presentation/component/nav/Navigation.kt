package com.unlam.feat.presentation.component.nav

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.map.FeatMap
import com.unlam.feat.presentation.component.map.FeatMapWhitMaker
import com.unlam.feat.presentation.component.map.Marker
import com.unlam.feat.presentation.view.events.Event
import com.unlam.feat.presentation.view.events.EventViewModel
import com.unlam.feat.presentation.view.events.add_event.AddEventViewModel
import com.unlam.feat.presentation.view.events.add_event.AddNewEventScreen
import com.unlam.feat.presentation.view.home.Home
import com.unlam.feat.presentation.view.home.HomeViewModel
import com.unlam.feat.presentation.view.login.LoginScreen
import com.unlam.feat.presentation.view.register.Register
import com.unlam.feat.presentation.view.search.Search
import com.unlam.feat.presentation.view.search.SearchViewModel
import com.unlam.feat.presentation.view.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        splash(navController)

        login(navController)
        register(navController)

        profile(navController)
        events(navController)
        home(navController)
        search(navController)
        invite(navController)

        addEvent(navController)
        searchList(navController)

    }

}

private fun NavGraphBuilder.splash(navController: NavHostController) {
    composable(Screen.Splash.route) {
        SplashScreen(navController)
    }
}

private fun NavGraphBuilder.login(navController: NavHostController) {
    composable(Screen.Login.route) {
        LoginScreen(navController)
    }
}

private fun NavGraphBuilder.register(navController: NavHostController) {
    composable(Screen.Register.route) {
        Register(navController)
    }
}

private fun NavGraphBuilder.profile(navController: NavHostController) {
    composable(Screen.Profile.route) {
        Box() {
            Text(text = "Hola mundo")
        }
    }
}

private fun NavGraphBuilder.events(
    navController: NavHostController,
) {
    composable(Screen.Events.route) {
        val eventViewModel: EventViewModel = hiltViewModel()
        val state = eventViewModel.state.value
        val isRefreshing = eventViewModel.isRefreshing.collectAsState()

        Event(
            state = state,
            onEvent = eventViewModel::onEvent,
            isRefreshing = isRefreshing.value,
            refreshData = eventViewModel::getEventsCreatedByUser
        )
    }
}

private fun NavGraphBuilder.home(
    navController: NavHostController,
) {
    composable(Screen.Home.route) {
        val homeViewModel: HomeViewModel = hiltViewModel()
        val state = homeViewModel.state.value
        val isRefreshing = homeViewModel.isRefreshing.collectAsState()

        Home(
            state = state,
            onEvent = homeViewModel::onEvent,
            isRefreshing = isRefreshing.value,
            refreshData = homeViewModel::getEventsByUser
        )
    }
}

private fun NavGraphBuilder.search(navController: NavHostController) {
    composable(Screen.Search.route) {
        val searchViewModel : SearchViewModel = hiltViewModel()
        var marker  = searchViewModel.marker.value
        
        FeatMapWhitMaker(onClick = {
        })
        
        if(marker.position != null){
            Log.e("RAO","click")

        }
    }
}

private fun NavGraphBuilder.invite(navController: NavHostController) {
    composable(Screen.Invite.route) {

        Button(onClick = {
            Firebase.auth.signOut()
            navController.popBackStack()
            navController.navigate(Screen.Login.route)
        }) {

        }
    }
}

private fun NavGraphBuilder.addEvent(navController: NavHostController) {
    composable(Screen.AddEvent.route) {

        val addEventViewModel: AddEventViewModel = hiltViewModel()
        val state = addEventViewModel.state.value

        AddNewEventScreen(
            state = state,
            createEvent = addEventViewModel::createEvent,
            navigateToEvents = {
                navController.popBackStack()
                navController.navigate(Screen.Events.route)
            },
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onValueChange = {
                addEventViewModel.onEvent(it)
            }
        )
    }
}

private fun NavGraphBuilder.searchList(
    navController: NavHostController,
) {
    composable(Screen.SearchList.route) {
        val eventViewModel: EventViewModel = hiltViewModel()
        val state = eventViewModel.state.value
        val isRefreshing = eventViewModel.isRefreshing.collectAsState()

        Search(
            state = state,
            onEvent = eventViewModel::onEvent,
            isRefreshing = isRefreshing.value,
            refreshData = eventViewModel::getEventsCreatedByUser
        )
    }
}