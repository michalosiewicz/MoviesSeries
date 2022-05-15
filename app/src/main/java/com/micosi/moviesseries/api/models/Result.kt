package com.micosi.moviesseries.api.models

data class Result(
    val _id: String,
    val actors: List<Any>,
    val countries: List<Country>,
    val createdAt: String,
    val description: String,
    val directors: List<Any>,
    val embedUrls: List<Any>,
    val episodes: List<Episode>,
    val escritors: List<Any>,
    val genres: List<Genre>,
    val image: String,
    val index: Int,
    val otherTitles: List<Any>,
    val rating: String,
    val release: String,
    val title: String,
    val titleOriginal: String,
    val updatedAt: String,
    val uuid: String,
    val year: String
)