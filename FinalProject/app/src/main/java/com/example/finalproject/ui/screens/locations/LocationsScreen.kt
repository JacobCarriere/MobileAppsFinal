package com.example.finalproject.ui.screens.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun LocationsScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = stringResource(id = R.string.screen_locations_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        val defaultLocation = LatLng(43.6532, -79.3832) // Toronto, Canada
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(defaultLocation, 12f)
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = LatLng(43.6545, -79.3800)),
                title = stringResource(R.string.cinema_1_title),
                snippet = stringResource(R.string.cinema_1_snippet)
            )
            Marker(
                state = MarkerState(position = LatLng(43.6510, -79.3900)),
                title = stringResource(R.string.cinema_2_title),
                snippet = stringResource(R.string.cinema_2_snippet)
            )
        }
    }
}
