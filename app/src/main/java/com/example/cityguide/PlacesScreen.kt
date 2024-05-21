package com.example.cityguide.ui.places

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cityguide.MainViewModel
import com.example.cityguide.api.Place
import kotlinx.coroutines.launch


@Composable
fun PlacesScreen(navController: NavController, viewModel: MainViewModel, apiKey: String) {
    var query by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(emptyList<Place>()) }
    var isLoading by remember { mutableStateOf(false) }
    var isEmpty by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                Log.d("PlacesScreen", "Query changed: $query")
                if (query.isNotEmpty()) {
                    coroutineScope.launch {
                        isLoading = true
                        try {
                            Log.d("PlacesScreen", "Loading search results")
                            viewModel.searchPlaces(query, apiKey)
                            searchResults = viewModel.searchResults.value
                            isEmpty = searchResults.isEmpty()
                            Log.d("PlacesScreen", "Search results updated: ${searchResults.size} results")
                        } catch (e: Exception) {
                            searchResults = emptyList()
                            isEmpty = true
                            Log.e("PlacesScreen", "Error during search", e)
                        } finally {
                            isLoading = false
                        }
                    }
                } else {
                    searchResults = emptyList()
                }
            },
            label = { Text("Search Places") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            searchResults.isEmpty() && !isLoading && isEmpty -> {
                Text("No results found", modifier = Modifier.align(Alignment.CenterHorizontally))
                Log.d("PlacesScreen", "No results found")
            }
            else -> {
                LazyColumn {
                    items(searchResults) { place ->
                        PlaceItem(place, navController)
                    }
                }
            }
        }
    }
}


@Composable
fun PlaceItem(place: Place, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("placedetail/${place.place_id}")
                Log.d("PlacesScreen", "Navigating to place detail: ${place.place_id}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = place.name, style = MaterialTheme.typography.titleLarge)
            place.vicinity?.let {
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
