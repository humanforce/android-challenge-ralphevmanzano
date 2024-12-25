package com.humanforce.humanforceandroidengineeringchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.WeatherTheme
import com.humanforce.humanforceandroidengineeringchallenge.navigation.Screen
import com.humanforce.humanforceandroidengineeringchallenge.navigation.TopLevelRoute
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.humanforce.humanforceandroidengineeringchallenge.feature.cities.CitiesScreen
import com.humanforce.humanforceandroidengineeringchallenge.feature.home.HomeScreen
import com.humanforce.humanforceandroidengineeringchallenge.feature.search.SearchScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val routes = listOf(
            TopLevelRoute("Home", Screen.Home, Icons.Filled.Home),
            TopLevelRoute("Cities", Screen.Cities, Icons.Filled.LocationOn),
        )

        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            routes.forEach { route ->
                                NavigationBarItem(
                                    icon = { Icon(route.icon, contentDescription = route.name) },
                                    label = { Text(route.name) },
                                    selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true,
                                    onClick = {
                                        navController.navigate(route.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<Screen.Home> {
                                HomeScreen()
                            }
                            composable<Screen.Cities> {
                                CitiesScreen()
                            }
                            composable<Screen.Search> {
                                SearchScreen()
                            }
                        }
                    }
                )
            }
        }

    }
}