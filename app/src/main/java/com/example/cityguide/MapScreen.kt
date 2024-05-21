package com.example.cityguide.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.cityguide.api.Place
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.runtime.*
import com.google.android.gms.maps.CameraUpdateFactory

@Composable
fun MapScreen(navController: NavController, places: List<Place>, userLocation: LatLng?) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(userLocation) {
        userLocation?.let {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(it, 12f)
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            // Handle map click if needed
        },
        onMapLongClick = { latLng ->
            // Handle map long click if needed
        }
    ) {
        places.forEach { place ->
            val position = LatLng(place.geometry.location.lat, place.geometry.location.lng)
            Marker(
                state = rememberMarkerState(position = position),
                title = place.name,
                snippet = place.vicinity,
                onClick = {
                    // Navigate to PlaceDetailScreen with the selected place information
                    val selectedPlace = places.find { p ->
                        p.geometry.location.lat == it.position.latitude &&
                                p.geometry.location.lng == it.position.longitude
                    }
                    selectedPlace?.let {
                        navController.navigate("placedetail/${it.name}/${it.vicinity}")
                    }
                    true
                }
            )
        }

        userLocation?.let {
            Marker(
                state = rememberMarkerState(position = it),
                title = "You are here"
            )
        }
    }
}