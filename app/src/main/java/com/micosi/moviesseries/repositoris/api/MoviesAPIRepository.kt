package com.micosi.moviesseries.repositoris.api

import com.micosi.moviesseries.api.RetrofitInstance
import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.db.DBReference.authFB
import com.micosi.moviesseries.mappers.ApiMovieToListMovieMapper
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State
import java.util.*

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
            DBReference.seriesUnseenReference.child(authFB.currentUser!!.uid + movie.title)
                .setValue(movie)
            State.Success("The series has been successfully add.")
        } catch (e: Exception) {
            State.Error("The series could not be added.")
        }
    }

    fun addMovieToDB(movie: Movie): State<String> {
        return try {
            DBReference.moviesUnseenReference.child(authFB.currentUser!!.uid + movie.title)
                .setValue(movie)
            State.Success("The movie has been successfully add.")
        } catch (e: Exception) {
            State.Error("The movie could not be added")
        }
    }
}