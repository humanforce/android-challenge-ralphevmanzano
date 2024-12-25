package com.humanforce.humanforceandroidengineeringchallenge.feature.cities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CitiesScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("Cities Screen", modifier = modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun CitiesScreenPreview() {
    CitiesScreen()
}