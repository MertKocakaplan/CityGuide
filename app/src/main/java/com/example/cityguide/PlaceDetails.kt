package com.example.cityguide.api

data class PlaceDetails(
    val place_id: String,
    val name: String,
    val address: String?,
    val phoneNumber: String?,
    val website: String?,
    val rating: Double?,
    val reviews: List<Review>?
)

data class Review(
    val authorName: String,
    val text: String
)
