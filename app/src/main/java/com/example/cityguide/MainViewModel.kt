package com.example.cityguide

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityguide.api.Place
import com.example.cityguide.api.PlacesService
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class MainViewModel : ViewModel() {
    private val placesService = PlacesService.create()
    val places: MutableStateFlow<List<Place>> = MutableStateFlow(emptyList())
    val placeDetails: MutableStateFlow<Place?> = MutableStateFlow(null)
    val popularPlaces: MutableStateFlow<List<Place>> = MutableStateFlow(emptyList())
    val userLocation: MutableStateFlow<LatLng?> = MutableStateFlow(null)
    private val _searchResults: MutableStateFlow<List<Place>> = MutableStateFlow(emptyList())
    val searchResults: StateFlow<List<Place>> = _searchResults


    private val _favorites: MutableStateFlow<List<Place>> = MutableStateFlow(emptyList())
    val favorites: StateFlow<List<Place>> = _favorites


    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null

    fun setUserLocation(lat: Double, lng: Double) {
        userLocation.value = LatLng(lat, lng)
    }

    fun startLocationUpdates(context: Context) {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    updateLocation(location)
                }
            }
        }

        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun updateLocation(location: Location) {
        setUserLocation(location.latitude, location.longitude)
    }

    fun fetchNearbyPlaces(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = placesService.getNearbyPlaces(
                    location = "41.0082,28.9784",
                    radius = 1500,
                    apiKey = apiKey
                )
                places.value = response.results
                Log.d("MainViewModel", "Nearby places fetched: ${response.results}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching nearby places", e)
            }
        }
    }

    fun fetchPlaceDetails(placeId: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = placesService.getPlaceDetails(placeId, apiKey)
                placeDetails.value = response.result
                Log.d("MainViewModel", "Place details fetched: ${response.result}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching place details", e)
            }
        }
    }

    fun fetchPopularPlaces(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = placesService.searchPlaces(query = city, apiKey = apiKey)
                popularPlaces.value = response.results.sortedByDescending { it.rating }.take(10)
                Log.d("MainViewModel", "Popular places fetched for city: $city, results: ${response.results}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching popular places", e)
            }
        }
    }

    fun fetchPopularPlacesByCity(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = placesService.searchPlaces(query = city, apiKey = apiKey)
                val location = response.results.firstOrNull()?.geometry?.location
                location?.let {
                    val latLng = "${it.lat},${it.lng}"
                    val nearbyResponse = placesService.getNearbyPlaces(
                        location = latLng,
                        radius = 10000,
                        apiKey = apiKey
                    )
                    popularPlaces.value = nearbyResponse.results.sortedByDescending { it.rating }.take(10)
                    Log.d("MainViewModel", "Popular places fetched for city: $city, results: ${nearbyResponse.results}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching popular places by city", e)
            }
        }
    }

    fun searchPlaces(query: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = placesService.searchPlaces(query, apiKey)
                _searchResults.value = response.results
                Log.d("MainViewModel", "Search results fetched for query: $query, results: ${response.results}")
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error searching places", e)
                _searchResults.value = emptyList()
            }
        }
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
    }

    // Favorilere ekleme ve çıkarma fonksiyonları
    fun addToFavorites(place: Place) {
        if (!_favorites.value.contains(place)) {
            _favorites.value = _favorites.value + place
        }
    }

    fun removeFromFavorites(place: Place) {
        _favorites.value = _favorites.value - place
    }

    fun isFavorite(place: Place): Boolean {
        return _favorites.value.contains(place)
    }

    // Bildirim Gönderme Fonksiyonu
    fun sendLocationPermissionDeniedNotification(context: Context) {
        val channelId = "location_permission"
        val channelName = "Location Permission"
        val notificationId = 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Notifications for location permission denial"
            }
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Location Permission Denied")
            .setContentText("Location permission was denied. Please enable it in settings.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }
}
