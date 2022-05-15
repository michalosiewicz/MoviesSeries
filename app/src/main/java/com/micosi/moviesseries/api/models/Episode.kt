package com.micosi.moviesseries.api.models

data class Episode(
    val _id: String,
    val createdAt: String,
    val episode: Int,
    val season: Int,
    val serie: String,
    val title: String,
    val updatedAt: String,
    val uuid: String
)