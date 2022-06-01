package com.unlam.feat.presentation.component.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.view.events.Event
import com.unlam.feat.presentation.view.events.EventViewModel
import com.unlam.feat.presentation.view.events.add_event.AddEventEvent
import com.unlam.feat.presentation.view.events.add_event.AddEventViewModel
import com.unlam.feat.presentation.view.events.add_event.AddNewEventScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        login(navController)
        register(navController)

        profile(navController)
        events(navController)
        home(navController)
        search(navController)
        invite(navController)

        addEvent(navController)

    }

}

private fun NavGraphBuilder.login(navController: NavHostController) {
    composable(Screen.Login.route) {

    }
}

private fun NavGraphBuilder.register(navController: NavHostController) {
    composable(Screen.Profile.route) {
        Box() {
            Text(text = "Hola mundo")
        }
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
            isRefreshing = isRefreshing.value,
            refreshData = eventViewModel::getEventsCreatedByUser
        )
    }
}

private fun NavGraphBuilder.home(
    navController: NavHostController,
) {
    composable(Screen.Home.route) {
        Box() {
            Text(text = "Hola mundo")
        }
    }
}

private fun NavGraphBuilder.search(navController: NavHostController) {
    composable(Screen.Search.route) {
        Box() {
            Text(text = "Hola mundo")
        }
    }
}

private fun NavGraphBuilder.invite(navController: NavHostController) {
    composable(Screen.Invite.route) {
        Box() {
            Text(text = "Hola mundo")
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
            onValueChange = {
                when(it){
                    is AddEventEvent.EnteredName -> {
//                        state.event.name = it
                    }
                }
            }
        )
    }
}