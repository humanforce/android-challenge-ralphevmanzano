package com.humanforce.humanforceandroidengineeringchallenge

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherViewModel
import com.humanforce.humanforceandroidengineeringchallenge.feature.cities.CitiesScreen
import com.humanforce.humanforceandroidengineeringchallenge.feature.home.HomeScreen
import com.humanforce.humanforceandroidengineeringchallenge.feature.search.SearchScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, proceed with the functionality
                getCurrentLocation()
            } else {
                // Permission denied, handle appropriately
                Toast.makeText(
                    this,
                    "We need your location to show the weather forecast.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

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
                val showBottomBar = !(currentDestination?.hasRoute(Screen.Search::class) ?: true)

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
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
                        }
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<Screen.Home> {
                                HomeScreen(
                                    weatherViewModel = weatherViewModel,
                                    onSearchClick = {
                                        navController.navigate(Screen.Search)
                                    }
                                )
                            }
                            composable<Screen.Cities> {
                                CitiesScreen(
                                    weatherViewModel = weatherViewModel,
                                    onSearchClick = {
                                        navController.navigate(Screen.Search)
                                    },
                                    onCityClick = {
                                        // go back to home and show weather for the selected city
                                        navController.popBackStack()
                                        weatherViewModel.getCurrentWeatherAndForecast(it.lat, it.long, false)
                                    }
                                )
                            }
                            composable<Screen.Search> {
                                SearchScreen(
                                    onCityClicked = {
                                        // go back to home and show weather for the selected city
                                        navController.popBackStack()
                                        weatherViewModel.getCurrentWeatherAndForecast(it.lat, it.long, false)
                                    },
                                    onNavigateBack = {
                                        navController.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }

        checkAndRequestLocationPermission()
    }

    private fun checkAndRequestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted check location settings
                checkLocationSettings(
                    onSuccess = {
                        getCurrentLocation()
                    },
                    onFailure = {
                        Toast.makeText(this, "Please enable location settings.", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Show rationale to the user
                showRationaleDialog()
            }

            else -> {
                // Request permission
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun checkLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build()

        val settingsClient = LocationServices.getSettingsClient(this)

        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    // Location settings are not satisfied, but can be resolved
                    try {
                        exception.startResolutionForResult(this, LOCATION_SERVICES_REQUEST_CODE)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        onFailure(sendEx)
                    }
                } else {
                    onFailure(exception)
                }
            }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
            .setMessage("We need your location to show the weather forecast.")
            .setPositiveButton("Allow") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        val fusedLocationProviderClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    // Use the location
                    Log.e("Location", "Latitude: $latitude, Longitude: $longitude")
                    weatherViewModel.getCurrentWeatherAndForecast(latitude, longitude, true)
                    fusedLocationProviderClient.removeLocationUpdates(this)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    companion object {
        const val LOCATION_SERVICES_REQUEST_CODE = 102
    }
}