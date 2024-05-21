package com.example.cityguide.api

data class PlacesResponse(
    val results: List<Place>
)

data class Place(
    val place_id: String,
    val name: String,
    val vicinity: String?,
    val geometry: Geometry,
    val address: String?,
    val phoneNumber: String?,
    val website: String?,
    val rating: Double?,
    val reviews: List<Review>?
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)
