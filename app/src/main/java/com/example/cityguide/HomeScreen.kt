package com.example.cityguide.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cityguide.R
import com.example.cityguide.ui.theme.Typography



data class Place(val name: String, val description: String, val imageUrl: Int, val paragraph: String)

val samplePlaces = listOf(
    Place(
        "Central Park",
        "A large public park in New York City.",
        R.drawable.central_park,
        "Central Park, located in the heart of New York City, is a green oasis amidst the bustling metropolis. Spanning over 843 acres, it is a sanctuary for both residents and visitors alike, offering a variety of activities such as boating, horse carriage rides, and picturesque picnics. Designed by Frederick Law Olmsted and Calvert Vaux, this iconic park features lush landscapes, tranquil water bodies, and historic structures, making it a beloved destination for nature lovers and urban explorers."
    ),
    Place(
        "Eiffel Tower",
        "An iconic tower located in Paris, France.",
        R.drawable.eiffel_tower,
        "The Eiffel Tower, an enduring symbol of Paris and one of the most recognizable structures in the world, was constructed in 1889 for the Exposition Universelle (World's Fair). Standing at 324 meters tall, it was the tallest man-made structure until the completion of the Chrysler Building in 1930. Visitors can ascend its three levels to enjoy panoramic views of the city, dine in exquisite restaurants, and marvel at the tower's intricate iron lattice work. The Eiffel Tower remains a testament to French engineering and a must-see landmark for any traveler."
    ),
    Place(
        "Great Wall of China",
        "A historic wall stretching across northern China.",
        R.drawable.great_wall_of_china,
        "The Great Wall of China, an ancient series of walls and fortifications, stretches over 13,000 miles across China's northern borders. Originally built to protect Chinese states from invasions, the wall's construction began as early as the 7th century BC and continued for centuries. Its impressive length and engineering prowess make it one of the greatest architectural achievements in history. Walking along the Great Wall offers breathtaking views and a deep sense of history, connecting visitors to the legacy of ancient Chinese civilizations."
    ),
    Place(
        "Colosseum",
        "An ancient amphitheater in Rome, Italy.",
        R.drawable.colosseum,
        "The Colosseum, an iconic symbol of Imperial Rome, is an ancient amphitheater that once hosted gladiatorial contests, animal hunts, and public spectacles. Completed in AD 80, it could hold up to 80,000 spectators, making it the largest amphitheater ever built. Despite earthquakes and stone-robbers, much of the Colosseum remains intact today, offering a glimpse into the grandeur and brutality of ancient Roman entertainment. Its enduring legacy continues to fascinate historians, architects, and millions of tourists from around the world."
    ),
    Place(
        "Sydney Opera House",
        "A multi-venue performing arts center in Sydney, Australia.",
        R.drawable.sydney_opera_house,
        "The Sydney Opera House, with its distinctive sail-like design, is one of the most famous and photographed buildings in the world. Completed in 1973, it was designed by Danish architect Jørn Utzon and has become a symbol of Australia's creativity and innovation. The Opera House hosts over 1,500 performances annually, ranging from opera and ballet to theatre and music concerts. Its stunning location on Bennelong Point, overlooking Sydney Harbour, makes it a cultural and architectural marvel that draws visitors from across the globe."
    )
)

@Composable
fun HomeScreen() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(samplePlaces) { place ->
            PlaceCard(place)
        }
    }
}

@Composable
fun PlaceCard(place: Place) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(text = place.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = place.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = place.imageUrl),
                contentDescription = place.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = place.paragraph, style = MaterialTheme.typography.bodySmall)
        }
    }
}