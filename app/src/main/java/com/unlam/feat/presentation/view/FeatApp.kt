package com.unlam.feat.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.component.nav.BottomNavigationItem
import com.unlam.feat.presentation.component.nav.FloatingButtonNavigation
import com.unlam.feat.presentation.component.nav.Navigation
import com.unlam.feat.presentation.component.nav.NavigationItem

@Composable
fun FeatApp(
    navController: NavHostController,
    showBottomBar: Boolean = false,
    navigationItems: List<NavigationItem> = listOf(
        NavigationItem(
            route = Screen.Profile.route,
            icon = Icons.Outlined.Person,
            contentDescription = "Profile"
        ),
        NavigationItem(
            route = Screen.Events.route,
            icon = Icons.Outlined.Event,
            contentDescription = "Events",
        ),
        NavigationItem(
            route = Screen.Home.route,
            icon = Icons.Outlined.Home,
            contentDescription = "Home",
        ),
        NavigationItem(
            route = Screen.Search.route,
            icon = Icons.Outlined.Search,
            contentDescription = "Search"
        ),
        NavigationItem(
            route = Screen.Invitation.route,
            icon = Icons.Outlined.Mail,
            contentDescription = "Invite",
            alertCount = null
        ),
    )
) {

    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colors.surface,
                        cutoutShape = CircleShape,
                    ) {
                        BottomNavigation(
                            backgroundColor = MaterialTheme.colors.surface,
                            elevation = 0.dp
                        ) {
                            navigationItems.forEach { item ->
                                BottomNavigationItem(
                                    icon = item.icon,
                                    contentDescription = item.contentDescription,
                                    selected = item.route == navController.currentDestination?.route,
                                    alertCount = item.alertCount,
                                    enabled = item.icon != null
                                ) {
                                    if (navController.currentDestination?.route != item.route) {
                                        navController.navigate(item.route)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            floatingActionButton = {
                var floatingButtonNavigation: FloatingButtonNavigation? = null
                when (navController.currentDestination?.route) {
                    Screen.Events.route -> {
                        floatingButtonNavigation = FloatingButtonNavigation(
                            route = Screen.AddEvent.route,
                            description = "Add Event"
                        )
                    }
                    /*Screen.Search.route -> {
                        floatingButtonNavigation = FloatingButtonNavigation(
                            route = Screen.SearchEventDetail.route,
                            icon = Icons.Outlined.Search,
                            description = "Search"
                        )

                    }*/
                    Screen.DetailEventHome.route + "/{idEvent}" -> {
                        floatingButtonNavigation = FloatingButtonNavigation(
                            route = Screen.Chat.route,
                            icon = Icons.Outlined.Chat,
                            description = "Chat"
                        )
                    }

                }

                if (floatingButtonNavigation != null) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(floatingButtonNavigation.route!!)
                        },
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White,
                        modifier = if (floatingButtonNavigation.description == "Chat") Modifier.padding(
                            bottom = 70.dp
                        ) else Modifier
                    ) {
                        Icon(
                            imageVector = floatingButtonNavigation.icon,
                            contentDescription = floatingButtonNavigation.description
                        )
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(
                        bottom = it.calculateBottomPadding()
                    )
            ) {
                Navigation(
                    navController = navController,
                )
            }
        }
    }
}