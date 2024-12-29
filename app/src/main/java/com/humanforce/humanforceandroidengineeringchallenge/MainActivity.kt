package com.humanforce.humanforceandroidengineeringchallenge

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.NoNetworkBanner
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.WeatherTheme
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.R
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherViewModel
import com.humanforce.humanforceandroidengineeringchallenge.feature.cities.CitiesScreen
import com.humanforce.humanforceandroidengineeringchallenge.feature.cities.CityPreviewScreen
import com.humanforce.humanforceandroidengineeringchallenge.feature.home.HomeScreen
import com.humanforce.humanforceandroidengineeringchallenge.feature.search.SearchScreen
import com.humanforce.humanforceandroidengineeringchallenge.navigation.Screen
import com.humanforce.humanforceandroidengineeringchallenge.navigation.TopLevelRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var networkConnectivityObserver: NetworkConnectivityObserver

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

            when {
                fineGranted -> {
                    checkLocationSettings()
                }
                coarseGranted -> {
                    checkLocationSettings()
                }
                else -> {
                    weatherViewModel.onLocationPermissionDenied()
                }
            }
        }

    private val locationServiceLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            weatherViewModel.onLocationServicesEnabled()
            getCurrentLocation()
        } else {
            Toast.makeText(this, getString(R.string.please_enable_location), Toast.LENGTH_SHORT).show()
            weatherViewModel.onLocationServicesDisabled()
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
                val uiState by weatherViewModel.uiState.collectAsStateWithLifecycle()
                val showBottomBar = !(currentDestination?.hasRoute(Screen.Search::class) ?: true ||
                        currentDestination?.hasRoute(Screen.CityPreview::class) ?: true)
                val snackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    contentWindowInsets = WindowInsets(0),
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                    bottomBar = {
                        if (showBottomBar) {
                            Column {
                                if (uiState.hasNoInternet) {
                                    NoNetworkBanner(modifier = Modifier.fillMaxWidth())
                                }
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
                        }
                    },
                    content = { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            NavHost(
                                navController = navController,
                                startDestination = Screen.Home,
                            ) {
                                composable<Screen.Home> {
                                    HomeScreen(weatherViewModel = weatherViewModel)
                                }
                                composable<Screen.Cities> {
                                    CitiesScreen(
                                        weatherViewModel = weatherViewModel,
                                        onSearchClick = {
                                            navController.navigate(Screen.Search)
                                        },
                                        onCityClick = {
                                            navController.popBackStack()
                                            weatherViewModel.getCurrentWeatherAndForecast(it.lat, it.long, false)
                                        },
                                        onClearClick = {
                                            weatherViewModel.removeCityToFavorites(it)
                                        }
                                    )
                                }
                                composable<Screen.Search> {
                                    SearchScreen(
                                        onCityClicked = {
                                            val coordinates = "${it.lat},${it.long}"
                                            navController.navigate(Screen.CityPreview(coordinates))
                                        },
                                        onNavigateBack = {
                                            navController.popBackStack()
                                        }
                                    )
                                }
                                composable<Screen.CityPreview> {
                                    CityPreviewScreen(
                                        modifier = Modifier,
                                        onBackButtonClick = {
                                            navController.popBackStack()
                                        },
                                        onFavoriteClick = {
                                            navController.popBackStack(Screen.Search, inclusive = true)
                                        }
                                    )
                                }
                            }
                        }

                        if (uiState.error != null) {
                            LaunchedEffect(Unit) {
                                snackBarHostState.showSnackbar(
                                    message = uiState.error ?: getString(R.string.unexpected_error),
                                    duration = SnackbarDuration.Short
                                )
                                weatherViewModel.consumeError()
                            }
                        }

                        if (uiState.hasNoLocationPermission) {
                            val context = LocalContext.current
                            LaunchedEffect(Unit) {
                                val result = snackBarHostState.showSnackbar(
                                    message = getString(R.string.permission_denied),
                                    duration = SnackbarDuration.Indefinite,
                                    actionLabel = getString(R.string.settings),
                                )

                                // User clicked the "Settings" action
                                if (result == SnackbarResult.ActionPerformed) {
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data = Uri.fromParts("package", context.packageName, null)
                                    }
                                    context.startActivity(intent)
                                    weatherViewModel.resetPermissionFlag()
                                }
                            }
                        }
                    }
                )
            }
        }

        networkConnectivityObserver = NetworkConnectivityObserver(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                networkConnectivityObserver.isConnected.collectLatest { isConnected ->
                    weatherViewModel.onNetworkConnectionStateChange(isConnected)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkAndRequestLocationPermission()
    }

    override fun onDestroy() {
        networkConnectivityObserver.unregisterCallback()
        super.onDestroy()
    }

    private fun checkAndRequestLocationPermission() {
        when {
            hasLocationPermission() -> {
                // Permission already granted check location settings
                weatherViewModel.resetPermissionFlag()
                checkLocationSettings()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // Show rationale to the user
                showPermissionRationaleDialog()
            }

            else -> {
                // Request permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun checkLocationSettings() {
        requestLocationSettings(
            onSuccess = {
                getCurrentLocation()
            },
            onFailure = {
                Toast.makeText(
                    this,
                    getString(R.string.please_enable_location),
                    Toast.LENGTH_SHORT
                ).show()
                weatherViewModel.onLocationServicesDisabled()
            }
        )
    }

    private fun requestLocationSettings(onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(false)
            .build()

        val settingsClient = LocationServices.getSettingsClient(this)

        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                        locationServiceLauncher.launch(intentSenderRequest)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        onFailure(sendEx)
                    }
                } else {
                    onFailure(exception)
                }
            }
    }

    private fun showPermissionRationaleDialog() {
        MaterialAlertDialogBuilder(this)
            .setMessage(getString(R.string.we_need_your_location))
            .setPositiveButton(getString(R.string.allow)) { _, _ ->
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )                }
            .setNegativeButton(getString(R.string.deny)) { dialog, _ ->
                dialog.dismiss()
                weatherViewModel.onLocationPermissionDenied()
            }
            .setCancelable(false)
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
                    weatherViewModel.getCurrentWeatherAndForecast(latitude, longitude, true)
                    fusedLocationProviderClient.removeLocationUpdates(this)
                }
            }
        }

        if (hasLocationPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}