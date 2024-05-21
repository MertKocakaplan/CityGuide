package com.example.cityguide.ui.popular

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cityguide.MainViewModel

data class Country(val name: String, val cities: List<String>)

@Composable
fun PopularScreen(navController: NavHostController, viewModel: MainViewModel = viewModel(), apiKey: String) {
    val countries = loadCountries()
    Log.d("PopularScreen", "Loaded countries: $countries")
    var selectedCountry by remember { mutableStateOf<Country?>(null) }
    var selectedCity by remember { mutableStateOf("") }
    val popularPlaces by viewModel.popularPlaces.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Select Country", style = MaterialTheme.typography.titleLarge)

        var countryQuery by remember { mutableStateOf("") }
        val filteredCountries = countries.filter { it.name.contains(countryQuery, ignoreCase = true) }

        BasicTextField(
            value = countryQuery,
            onValueChange = { query ->
                countryQuery = query
                selectedCountry = null
                selectedCity = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the done action if needed
                }
            ),
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                        .padding(8.dp)
                ) {
                    if (countryQuery.isEmpty()) {
                        Text(text = "Country", color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        )

        if (filteredCountries.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxWidth().background(Color.White, shape = MaterialTheme.shapes.medium).padding(8.dp)) {
                items(filteredCountries) { country ->
                    DropdownMenuItem(
                        text = { Text(country.name) },
                        onClick = {
                            selectedCountry = country
                            countryQuery = country.name
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedCountry != null) {
            Text(text = "Select City", style = MaterialTheme.typography.titleLarge)

            var cityQuery by remember { mutableStateOf("") }
            val filteredCities = selectedCountry!!.cities.filter { it.contains(cityQuery, ignoreCase = true) }

            BasicTextField(
                value = cityQuery,
                onValueChange = { query ->
                    cityQuery = query
                    selectedCity = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle the done action if needed
                    }
                ),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .background(Color.White, shape = MaterialTheme.shapes.medium)
                            .padding(8.dp)
                    ) {
                        if (cityQuery.isEmpty()) {
                            Text(text = "City", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )

            if (filteredCities.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxWidth().background(Color.White, shape = MaterialTheme.shapes.medium).padding(8.dp)) {
                    items(filteredCities) { city ->
                        DropdownMenuItem(
                            text = { Text(city) },
                            onClick = {
                                selectedCity = city
                                cityQuery = city
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedCity.isNotEmpty()) {
            Button(onClick = { viewModel.fetchPopularPlacesByCity(selectedCity, apiKey) }) {
                Text(text = "Show Popular Places")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (popularPlaces.isNotEmpty()) {
                Text(text = "Popular Places in $selectedCity", style = MaterialTheme.typography.titleLarge)

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(popularPlaces) { place ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { navController.navigate("placedetail/${place.place_id}") }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = place.name, style = MaterialTheme.typography.titleLarge)
                                place.vicinity?.let {
                                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            } else {
                Text(text = "", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

fun loadCountries(): List<Country> {
    val countries = listOf(
        Country("USA", listOf("New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose")),
        Country("Canada", listOf("Toronto", "Vancouver", "Montreal", "Calgary", "Edmonton", "Ottawa", "Winnipeg", "Quebec City", "Hamilton", "Kitchener")),
        Country("UK", listOf("London", "Manchester", "Birmingham", "Leeds", "Glasgow", "Liverpool", "Newcastle", "Sheffield", "Bristol", "Edinburgh")),
        Country("Australia", listOf("Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide", "Gold Coast", "Canberra", "Newcastle", "Wollongong", "Geelong")),
        Country("France", listOf("Paris", "Lyon", "Marseille", "Toulouse", "Nice", "Nantes", "Strasbourg", "Montpellier", "Bordeaux", "Lille")),
        Country("Germany", listOf("Berlin", "Munich", "Frankfurt", "Hamburg", "Cologne", "Stuttgart", "Düsseldorf", "Dortmund", "Essen", "Leipzig")),
        Country("Japan", listOf("Tokyo", "Osaka", "Kyoto", "Yokohama", "Nagoya", "Sapporo", "Kobe", "Fukuoka", "Kawasaki", "Hiroshima")),
        Country("India", listOf("Mumbai", "Delhi", "Bangalore", "Hyderabad", "Ahmedabad", "Chennai", "Kolkata", "Surat", "Pune", "Jaipur")),
        Country("Brazil", listOf("São Paulo", "Rio de Janeiro", "Brasília", "Salvador", "Fortaleza", "Belo Horizonte", "Manaus", "Curitiba", "Recife", "Porto Alegre")),
        Country("South Korea", listOf("Seoul", "Busan", "Incheon", "Daegu", "Daejeon", "Gwangju", "Suwon", "Ulsan", "Changwon", "Goyang")),
        Country("China", listOf("Beijing", "Shanghai", "Shenzhen", "Guangzhou", "Chengdu", "Chongqing", "Tianjin", "Wuhan", "Hangzhou", "Xi'an")),
        Country("Italy", listOf("Rome", "Milan", "Naples", "Turin", "Palermo", "Genoa", "Bologna", "Florence", "Bari", "Catania")),
        Country("Spain", listOf("Madrid", "Barcelona", "Valencia", "Seville", "Zaragoza", "Málaga", "Murcia", "Palma", "Las Palmas", "Bilbao")),
        Country("Russia", listOf("Moscow", "Saint Petersburg", "Kazan", "Novosibirsk", "Yekaterinburg", "Nizhny Novgorod", "Samara", "Omsk", "Rostov-on-Don", "Ufa")),
        Country("Mexico", listOf("Mexico City", "Guadalajara", "Monterrey", "Puebla", "Toluca", "Tijuana", "León", "Ciudad Juárez", "Querétaro", "Mérida")),
        Country("Netherlands", listOf("Amsterdam", "Rotterdam", "The Hague", "Utrecht", "Eindhoven", "Tilburg", "Groningen", "Almere", "Breda", "Nijmegen")),
        Country("Switzerland", listOf("Zurich", "Geneva", "Basel", "Lausanne", "Bern", "Winterthur", "Lucerne", "St. Gallen", "Lugano", "Biel/Bienne")),
        Country("Argentina", listOf("Buenos Aires", "Córdoba", "Rosario", "Mendoza", "La Plata", "San Miguel de Tucumán", "Mar del Plata", "Salta", "Santa Fe", "San Juan")),
        Country("South Africa", listOf("Johannesburg", "Cape Town", "Durban", "Pretoria", "Port Elizabeth", "Bloemfontein", "Nelspruit", "East London", "Kimberley", "Polokwane")),
        Country("Turkey", listOf("Istanbul", "Ankara", "Izmir", "Bursa", "Adana", "Gaziantep", "Konya", "Antalya", "Kayseri", "Mersin", "Manisa")),
        Country("Saudi Arabia", listOf("Riyadh", "Jeddah", "Mecca", "Medina", "Dammam", "Khobar", "Tabuk", "Abha", "Jizan", "Najran")),
        Country("New Zealand", listOf("Auckland", "Wellington", "Christchurch", "Hamilton", "Tauranga", "Dunedin", "Palmerston North", "Napier-Hastings", "Nelson", "Rotorua")),
        Country("Malaysia", listOf("Kuala Lumpur", "George Town", "Johor Bahru", "Ipoh", "Shah Alam", "Malacca City", "Kota Kinabalu", "Kuching", "Seremban", "Petaling Jaya")),
        Country("Egypt", listOf("Cairo", "Alexandria", "Giza", "Shubra El-Kheima", "Port Said", "Suez", "Luxor", "Mansoura", "Tanta", "Asyut"))
    )
    Log.d("PopularScreen", "loadCountries(): $countries")
    return countries
}
