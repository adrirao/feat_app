package com.unlam.feat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.unlam.feat.common.Screen
import com.unlam.feat.presentation.ui.theme.FeatTheme
import com.unlam.feat.presentation.view.FeatApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
//            ProvideWindowInsets {
                FeatTheme {
                    val navController  = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    FeatApp(
                        navController = navController,
                        showBottomBar = navBackStackEntry?.destination?.route in listOf(
                            Screen.Profile.route,
                            Screen.Events.route,
                            Screen.Search.route,
                            Screen.Invitation.route,
                            Screen.Home.route,
                        ),
                    )
                }
//            }
        }
    }
}
