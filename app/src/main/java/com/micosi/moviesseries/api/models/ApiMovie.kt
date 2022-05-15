package com.micosi.moviesseries.api.models

data class ApiMovie(
    val messageStatus: String,
    val results: List<Result>,
    val status: Int,
    val success: Boolean,
    val total_pages: Int,
    val total_results: Int
)