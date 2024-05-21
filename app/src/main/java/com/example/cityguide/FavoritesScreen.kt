package com.example.cityguide.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import com.example.cityguide.MainViewModel
import com.example.cityguide.api.Place

@Composable
fun FavoritesScreen(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    val favoritePlaces by viewModel.favorites.collectAsState()

    LazyColumn {
        items(favoritePlaces) { place ->
            Card(
                modifier = Modifier.padding(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = place.name, style = MaterialTheme.typography.titleLarge)
                    place.vicinity?.let {
                        Text(text = it, style = MaterialTheme.typography.bodyMedium)
                    }
                    Button(onClick = { navController.navigate("placedetail/${place.place_id}") }) {
                        Text(text = "View Details")
                    }
                }
            }
        }
    }
}
