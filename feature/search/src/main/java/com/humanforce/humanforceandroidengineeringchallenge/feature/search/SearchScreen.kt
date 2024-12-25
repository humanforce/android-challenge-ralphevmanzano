package com.humanforce.humanforceandroidengineeringchallenge.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("Search Screen", modifier = Modifier.align(Alignment.Center))
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen()
}