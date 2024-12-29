package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.LightGray
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.WeatherTheme
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.R

@Composable
fun NoNetworkBanner(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxWidth(), color = LightGray) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wifi_off),
                tint = Color.White,
                contentDescription = "No Internet Connection"
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.no_internet_connection),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun NoNetworkBannerPreview() {
    WeatherTheme {
        NoNetworkBanner()
    }
}