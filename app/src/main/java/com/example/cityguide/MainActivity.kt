package com.example.cityguide

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cityguide.ui.home.HomeScreen
import com.example.cityguide.ui.map.MapScreen
import com.example.cityguide.ui.placedetail.PlaceDetailScreen
import com.example.cityguide.ui.favorites.FavoritesScreen
import com.example.cityguide.ui.places.PlacesScreen
import com.example.cityguide.ui.theme.CityGuideTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import com.example.cityguide.ui.popular.PopularScreen
import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val viewModel: MainViewModel by viewModels {
                MainViewModelFactory(application)
            }
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                // Konum izni verildi
                viewModel.startLocationUpdates(this)
            } else {
                // Konum izni reddedildi
                viewModel.sendLocationPermissionDeniedNotification(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = "AIzaSyCIT4GyCJfb50aStL3UYb9aUSsfee-6kZ8"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))
            } else {
                // Konum izni zaten verilmiş
                val viewModel: MainViewModel by viewModels {
                    MainViewModelFactory(application)
                }
                viewModel.startLocationUpdates(this)
            }
        }

        setContent {
            CityGuideTheme {
                val viewModel: MainViewModel by viewModels {
                    MainViewModelFactory(application)
                }
                CityGuideApp(viewModel, apiKey)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CityGuideApp(viewModel: MainViewModel, apiKey: String) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        NavigationGraph(navController = navController, viewModel = viewModel, apiKey = apiKey)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Map,
        Screen.Places,
        Screen.Favorites,
        Screen.Popular
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: MainViewModel, apiKey: String) {
    NavHost(navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Map.route) {
            val places by viewModel.places.collectAsState()
            val userLocation by viewModel.userLocation.collectAsState()
            MapScreen(navController, places, userLocation)
        }
        composable(Screen.Favorites.route) { FavoritesScreen(navController, viewModel) }
        composable(Screen.Places.route) { PlacesScreen(navController, viewModel, apiKey) }
        composable(Screen.PlaceDetail.route + "/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")
            placeId?.let {
                LaunchedEffect(placeId) {
                    viewModel.fetchPlaceDetails(placeId, apiKey)
                }
                val place by viewModel.placeDetails.collectAsState()
                place?.let {
                    PlaceDetailScreen(navController = navController, place = it, viewModel = viewModel)
                }
            }
        }
        composable(Screen.Popular.route) { PopularScreen(navController, viewModel, apiKey) }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Map : Screen("map", "Map", Icons.Filled.LocationOn)
    object Places : Screen("places", "Places", Icons.Filled.Search)
    object Favorites : Screen("favorites", "Favorites", Icons.Filled.Favorite)
    object PlaceDetail : Screen("placedetail", "Place Detail", Icons.Filled.Info)
    object Popular : Screen("popular", "Popular", Icons.Filled.Star)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CityGuideTheme {
        val context = LocalContext.current
        val viewModel = MainViewModel(context.applicationContext as Application)
        CityGuideApp(viewModel, "AIzaSyCIT4GyCJfb50aStL3UYb9aUSsfee-6kZ8")
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}