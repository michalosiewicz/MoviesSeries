package com.micosi.moviesseries.repositoris.api

import com.micosi.moviesseries.api.RetrofitInstance
import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.mappers.ApiMovieToListMovieMapper
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State

class MoviesAPIRepository {

    private val apiMovieToListMovieMapper = ApiMovieToListMovieMapper()

    suspend fun getMovies(title: String, type: String): State<List<Movie>> {
        return try {
            val response = RetrofitInstance.retrofit.getMovies(type, title)
            if (response.isSuccessful) {
                State.Success(
                    apiMovieToListMovieMapper.map(response.body())
                )
            } else {
                State.Error(response.message())
            }
        } catch (e: Exception) {
            State.Error("Connection error")
        }
    }

    fun addSeriesToDB(movie: Movie): State<String> {
        return try {
            DBReference.seriesUnseenReference.child(movie.title).setValue(movie)
            State.Success("Correct addition")
        } catch (e: Exception) {
            State.Error("Connection error")
        }
    }

    fun addMovieToDB(movie: Movie): State<String> {
        return try {
            DBReference.moviesUnseenReference.child(movie.title).setValue(movie)
            State.Success("Correct addition")
        } catch (e: Exception) {
            State.Error("Connection error")
        }
    }
}