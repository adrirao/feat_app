package com.unlam.feat.presentation.component.nav


import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.unlam.feat.common.Screen
import com.unlam.feat.model.SportGeneric
import com.unlam.feat.presentation.component.FeatCircularProgress
import com.unlam.feat.model.ListSportId
import com.unlam.feat.model.Player
import com.unlam.feat.model.player_detail.PlayerDetail
import com.unlam.feat.presentation.view.config_profile.additional_information.ConfigProfileAdditionalInformationScreen
import com.unlam.feat.presentation.view.config_profile.additional_information.ConfigProfileAdditionalInformationViewModel
import com.unlam.feat.presentation.view.config_profile.address.ConfigProfileAddressEvent
import com.unlam.feat.presentation.view.config_profile.address.ConfigProfileAddressScreen
import com.unlam.feat.presentation.view.config_profile.address.ConfigProfileAddressViewModel
import com.unlam.feat.presentation.view.config_profile.availability.ConfigProfileAvailabilityEvent
import com.unlam.feat.presentation.view.config_profile.availability.ConfigProfileAvailabilityScreen
import com.unlam.feat.presentation.view.config_profile.availability.ConfigProfileAvailabilityViewModel
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataEvent
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataScreen
import com.unlam.feat.presentation.view.config_profile.personal_data.ConfigProfilePersonalDataViewModel
import com.unlam.feat.presentation.view.config_profile.sport.ConfigSportEvent
import com.unlam.feat.presentation.view.config_profile.sport.ConfigSportScreen
import com.unlam.feat.presentation.view.config_profile.sport.ConfigSportViewModel
import com.unlam.feat.presentation.view.config_profile.sport.sport_data.SportDataEvent
import com.unlam.feat.presentation.view.config_profile.sport.sport_data.SportDataScreen
import com.unlam.feat.presentation.view.config_profile.sport.sport_data.SportDataViewModel
import com.unlam.feat.presentation.view.edit_profile.Profile
import com.unlam.feat.presentation.view.edit_profile.ProfileViewModel
import com.unlam.feat.presentation.view.edit_profile.address.EditProfileAddressScreen
import com.unlam.feat.presentation.view.edit_profile.address.EditProfileAddressViewModel
import com.unlam.feat.presentation.view.edit_profile.personal_information.EditPersonalInformationViewModel
import com.unlam.feat.presentation.view.edit_profile.personal_information.PersonalInformation
import com.unlam.feat.presentation.view.edit_profile.player_information.PlayerInformationScreen
import com.unlam.feat.presentation.view.events.Event
import com.unlam.feat.presentation.view.events.EventViewModel
import com.unlam.feat.presentation.view.events.add_event.AddEventViewModel
import com.unlam.feat.presentation.view.events.add_event.AddNewEventScreen
import com.unlam.feat.presentation.view.events.detail_event.DetailEventScreen
import com.unlam.feat.presentation.view.events.detail_event.DetailEventViewModel
import com.unlam.feat.presentation.view.home.Home
import com.unlam.feat.presentation.view.home.HomeViewModel
import com.unlam.feat.presentation.view.home.detail_event.DetailEventHome
import com.unlam.feat.presentation.view.home.detail_event.DetailEventHomeEvent
import com.unlam.feat.presentation.view.home.detail_event.DetailEventHomeViewModel
import com.unlam.feat.presentation.view.invitation.InvitationScreen
import com.unlam.feat.presentation.view.invitation.InvitationViewModel
import com.unlam.feat.presentation.view.invitation.detail_invitation.DetailInvitationScreen
import com.unlam.feat.presentation.view.invitation.detail_invitation.DetailInvitationViewModel
import com.unlam.feat.presentation.view.login.LoginScreen
import com.unlam.feat.presentation.view.register.Register
import com.unlam.feat.presentation.view.search.Search
import com.unlam.feat.presentation.view.search.SearchViewModel
import com.unlam.feat.presentation.view.search.event_detail.SearchEventDetailScreen
import com.unlam.feat.presentation.view.search.event_detail.SearchEventDetailViewModel
import com.unlam.feat.presentation.view.splash.SplashScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        splash(navController)

        login(navController)
        configProfilePersonalData(navController)
        configProfileAddress(navController)
        configProfileAvailability(navController)
        configProfileAdditionalInformation(navController)
        configSport(navController)
        sportData(navController)
        register(navController)

        profile(navController)
        editProfileAddress(navController)
        editPersonalInformation(navController)
        playerInformation(navController)


        events(navController)
        home(navController)
        search(navController)
        invitation(navController)

        addEvent(navController)
        detailEvent(navController)
        searchEventDetail(navController)


        detailEventHome(navController)

        detailInvitation(navController)

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


fun NavGraphBuilder.configProfilePersonalData(navController: NavHostController) {
    composable(Screen.ConfigProfilePersonalData.route) {

        val configProfilePersonalDataViewModel: ConfigProfilePersonalDataViewModel = hiltViewModel()
        val state = configProfilePersonalDataViewModel.state.value

        ConfigProfilePersonalDataScreen(
            navController,
            state,
            onValueChange = { configProfilePersonalDataViewModel.onEvent(it) })
    }
}

private fun NavGraphBuilder.configProfileAddress(navController: NavHostController) {
    composable(Screen.ConfigProfileAddress.route) {

        val configProfileAddressViewModel: ConfigProfileAddressViewModel = hiltViewModel()
        val state = configProfileAddressViewModel.state.value

        ConfigProfileAddressScreen(
            navController,
            state,
            onValueChange = { configProfileAddressViewModel.onEvent(it) })
    }
}

private fun NavGraphBuilder.configProfileAvailability(navController: NavHostController) {
    composable(Screen.ConfigProfileAvailability.route) {

        val configProfileAvailabilityViewModel: ConfigProfileAvailabilityViewModel = hiltViewModel()
        val state = configProfileAvailabilityViewModel.state.value

        ConfigProfileAvailabilityScreen(
            navController,
            state,
            onValueChange = { configProfileAvailabilityViewModel.onEvent(it) })
    }
}

private fun NavGraphBuilder.configProfileAdditionalInformation(navController: NavHostController) {
    composable(Screen.ConfigProfileAdditionalInformation.route) {

        val configProfileAdditionalInformationViewModel: ConfigProfileAdditionalInformationViewModel =
            hiltViewModel()
        val state = configProfileAdditionalInformationViewModel.state.value

        ConfigProfileAdditionalInformationScreen(
            navController, state,
            onValueChange = { configProfileAdditionalInformationViewModel.onEvent(it) },
        )
    }
}

private fun NavGraphBuilder.configSport(navController: NavHostController) {
    composable(
        Screen.ConfigSport.route + "?listSportGenericId={listSportGenericId}",
        arguments = listOf(navArgument("listSportGenericId") { nullable = true })
    ) { backStackEntry ->
        val listSportGenericId: ListSportId?

        if (!backStackEntry.arguments?.getString("listSportGenericId").isNullOrEmpty()) {
            val listSportGenericIdJson = backStackEntry.arguments?.getString("listSportGenericId")
            val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(ListSportId::class.java).lenient()
            listSportGenericId = jsonAdapter.fromJson(requireNotNull(listSportGenericIdJson))
        } else {
            listSportGenericId = null
        }
        val configSportViewModel: ConfigSportViewModel = hiltViewModel()
        val state = configSportViewModel.state.value

        ConfigSportScreen(
            navController, state,
            onValueChange = { configSportViewModel.onEvent(it) },
            listSportGenericId
        )

    }
}

private fun NavGraphBuilder.sportData(navController: NavHostController) {
    composable(
        Screen.SportData.route + "/sportGeneric={sportGeneric}&listSportGenericId={listSportGenericId}",
        arguments = listOf(
            navArgument("sportGeneric") { type = NavType.StringType },
            navArgument("listSportGenericId") { type = NavType.StringType })
    ) { backStackEntry ->

        val sportGenericJson = backStackEntry.arguments?.getString("sportGeneric")
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(SportGeneric::class.java).lenient()
        val sportGeneric = jsonAdapter.fromJson(requireNotNull(sportGenericJson))

        val listSportGenericIdJson = backStackEntry.arguments?.getString("listSportGenericId")
        val jsonAdapterId = moshi.adapter(ListSportId::class.java).lenient()
        val listSportGenericId = jsonAdapterId.fromJson(requireNotNull(listSportGenericIdJson))

        val sportDataViewModel: SportDataViewModel = hiltViewModel()
        val state = sportDataViewModel.state.value

        LaunchedEffect(key1 = true) {
            if (sportGeneric != null) {
                sportDataViewModel.getDataSportScreen(sportGeneric.id)
            }
        }


        SportDataScreen(
            navController, state,
            onValueChange = { sportDataViewModel.onEvent(it) },
            requireNotNull(sportGeneric),
            requireNotNull(listSportGenericId)
        )

    }
}

private fun NavGraphBuilder.profile(navController: NavHostController) {
    composable(Screen.Profile.route) {
        Button(onClick = {
            Firebase.auth.signOut()
            navController.popBackStack(Screen.Home.route, inclusive = true)
            navController.navigate(Screen.Login.route)
        }) {

        }
        val profileViewModel: ProfileViewModel = hiltViewModel()
        val state = profileViewModel.state.value
        val isRefreshing = profileViewModel.isRefreshing.collectAsState()

        Profile(
            state = state,
            onEvent = profileViewModel::onEvent,
            isRefreshing = isRefreshing.value,
            refreshData = profileViewModel::getDetailProfile,
            onValueChange = {
                profileViewModel.onEvent(it)
            },
            navigateToAddress = {
                navController.navigate(
                    Screen.EditProfileAddress.route
                )
            },
            updatePersonPreferences = profileViewModel::updatePersonPreferences,
            navigateToPersonalInformation = {
                navController.navigate(
                    Screen.EditProfilePersonalInformation.route
                )
            },
            navigateToPlayerInformation = {
                navController.popBackStack()
                navController.navigate(Screen.PlayerInformation.route + "/${it}")
            }
        )
    }
}

private fun NavGraphBuilder.editProfileAddress(
    navController: NavHostController,
) {
    composable(
        route = Screen.EditProfileAddress.route,
    ) {
        val editProfileAddressViewModel: EditProfileAddressViewModel = hiltViewModel()
        val state = editProfileAddressViewModel.state.value

        EditProfileAddressScreen(
            navController,
            state,
            onValueChange = { editProfileAddressViewModel.onEvent(it) })
    }
}

private fun NavGraphBuilder.editPersonalInformation(
    navController: NavHostController,
) {
    composable(
        route = Screen.EditProfilePersonalInformation.route,
    ) {
        val editPersonalInformationViewModel: EditPersonalInformationViewModel = hiltViewModel()
        val state = editPersonalInformationViewModel.state.value

        PersonalInformation(
            navController,
            state,
            onValueChange = { editPersonalInformationViewModel.onEvent(it) },
            updatePerson = editPersonalInformationViewModel::updatePerson
        )
    }
}

private fun NavGraphBuilder.playerInformation(navController: NavHostController) {
    composable(
        Screen.PlayerInformation.route + "/{player}",
        arguments = listOf(navArgument("player") { type = NavType.StringType })
    ) { backStackEntry ->
        val player: PlayerDetail

        val playerJson = backStackEntry.arguments?.getString("player")
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(PlayerDetail::class.java).lenient()
        player = jsonAdapter.fromJson(requireNotNull(playerJson))!!

        PlayerInformationScreen(
            player
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
            refreshData = homeViewModel::getEventsForUser,
            navigateToDetail = {
                navController.navigate(
                    Screen.DetailEventHome.route + "/${it}"
                )
            }
        )
    }
}

private fun NavGraphBuilder.detailEventHome(
    navController: NavHostController,
) {
    composable(
        route = Screen.DetailEventHome.route + "/{idEvent}",
        arguments = Screen.DetailEventHome.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val detailEventHomeViewModel: DetailEventViewModel = hiltViewModel()
        val state = detailEventHomeViewModel.state.value

        LaunchedEffect(key1 = true) {
            detailEventHomeViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.playersApplied != null && state.playersConfirmed != null && state.playersSuggested != null) {
//            DetailEventScreen(state)
        }
    }
}

private fun NavGraphBuilder.search(navController: NavHostController) {
    composable(Screen.Search.route) {
        val searchViewModel: SearchViewModel = hiltViewModel()
        val state = searchViewModel.state.value
        val isRefreshing = searchViewModel.isRefreshing.collectAsState()
        Search(
            state = state,
            onEvent = searchViewModel::onEvent,
            isRefreshing = isRefreshing.value,
            refreshData = searchViewModel::getEventsSuggestedForUser,
            onClickCard = {
                navController.navigate(Screen.SearchEventDetail.route + "/${it.id}")
            }
        )
    }
}

private fun NavGraphBuilder.invitation(navController: NavHostController) {
    composable(Screen.Invitation.route) {
        val invitationViewModel: InvitationViewModel = hiltViewModel()
        val state = invitationViewModel.state.value
        val isRefreshing = invitationViewModel.isRefreshing.collectAsState()

        InvitationScreen(
            state = state,
            onEvent = invitationViewModel::onEvent,
            isRefreshing = isRefreshing.value,
            refreshData = invitationViewModel::getAllInvitationsForUser,
            navigateToDetail = {
                navController.navigate(
                    Screen.DetailInvitation.route + "/${it.id}"
                )
            }
        )

    }
}

private fun NavGraphBuilder.detailInvitation(
    navController: NavHostController,
) {
    composable(
        route = Screen.DetailInvitation.route + "/{idEvent}",
        arguments = Screen.DetailInvitation.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val detailInvitation: DetailInvitationViewModel = hiltViewModel()
        val state = detailInvitation.state.value

        LaunchedEffect(key1 = true) {
            detailInvitation.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.playersConfirmed != null) {
            DetailInvitationScreen(
                state,
                detailInvitation::onEvent,
                navigateToInvitation = {
                    navController.popBackStack()
                    navController.navigate(Screen.Invitation.route)
                }
            )
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
            refreshData = eventViewModel::getEventsCreatedByUser,
            navigateToDetail = {
                navController.navigate(
                    Screen.DetailEvent.route + "/${it.id}"
                )
            }
        )
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

private fun NavGraphBuilder.detailEvent(
    navController: NavHostController,
) {
    composable(
        route = Screen.DetailEvent.route + "/{idEvent}",
        arguments = Screen.DetailEvent.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val detailEventViewModel: DetailEventViewModel = hiltViewModel()
        val state = detailEventViewModel.state.value

        LaunchedEffect(key1 = true) {
            detailEventViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.playersApplied != null && state.playersConfirmed != null && state.playersSuggested != null) {
            DetailEventScreen(
                state = state,
                onEvent = detailEventViewModel::onEvent,
                refreshData = detailEventViewModel::refreshData,
                navigateToEvents = { navController.navigate(Screen.Events.route) })
        }
    }
}

private fun NavGraphBuilder.searchEventDetail(
    navController: NavHostController
) {
    composable(
        route = Screen.SearchEventDetail.route + "/{idEvent}",
        arguments = Screen.SearchEventDetail.arguments ?: listOf()
    ) {
        val idEvent = it.arguments?.getString("idEvent") ?: ""
        val searchEventDetailViewModel: SearchEventDetailViewModel = hiltViewModel()
        val state = searchEventDetailViewModel.state.value

        LaunchedEffect(key1 = true) {
            searchEventDetailViewModel.getDataDetailEvent(idEvent.toInt())
        }

        if (state.loading) {
            FeatCircularProgress()
        }

        if (state.event != null && state.playersConfirmed != null) {
            SearchEventDetailScreen(
                state = state,
                onEvent = searchEventDetailViewModel::onEvent,
                navigateToSearch = {
                    navController.navigate(Screen.Search.route)
                })
        }
    }
}



