package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    modifier: Modifier = Modifier,
    showSearch: Boolean = false,
    onSearchClick: () -> Unit = {  },
    isFavorite: Boolean = false,
    showFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {  },
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {  }
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        actions = {
            if (showFavorite) {
                // Favorite icon button
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }
            if (showSearch) {
                // Search icon button
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }
        },
        navigationIcon = {
            if (showBackButton) {
                // Back icon button
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }

            }
        }
    )
}

@Preview
@Composable
private fun WeatherAppBarPreview() {
    MaterialTheme {
        WeatherTopAppBar(
            showSearch = true,
            showFavorite = true,
            isFavorite = true
        )
    }
}