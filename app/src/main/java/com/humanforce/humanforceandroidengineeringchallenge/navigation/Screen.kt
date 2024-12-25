package com.humanforce.humanforceandroidengineeringchallenge.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Home: Screen

    @Serializable
    data object Search: Screen

    @Serializable
    data object Cities: Screen
}

data class TopLevelRoute<T: Any>(val name: String, val route: T, val icon: ImageVector)