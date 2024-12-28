package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ForecastItem(modifier: Modifier = Modifier, forecast: Forecast) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(forecast.day)
        GlideImage(
            modifier = Modifier
                .size(48.dp)
                .padding(vertical = 8.dp),
            imageModel = { forecast.weatherIconUrl },
            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
        )
        Text("${forecast.tempMax.toInt()}°")
        HorizontalDivider(
            modifier = Modifier
                .width(48.dp)
                .padding(vertical = 4.dp)
        )
        Text("${forecast.tempMin.toInt()}°", color = Color.Gray)
    }
}