package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerContainer

@Composable
fun WeatherShimmerSection(modifier: Modifier) {
    Card(shape = RoundedCornerShape(8.dp)) {
        ShimmerContainer(
            modifier = modifier,
            shimmer = Shimmer.Fade(
                baseColor = Color.White,
                highlightColor = Color.LightGray,
            )
        )
    }
}