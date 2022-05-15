package com.micosi.moviesseries.api.services

import com.micosi.moviesseries.api.models.ApiMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("/api/movies")
    suspend fun getMovies(
        @Query("type") type: String,
        @Query("query") title: String
    ): Response<ApiMovie>
}