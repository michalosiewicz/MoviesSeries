package com.micosi.moviesseries.repositoris.api

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.micosi.moviesseries.api.RetrofitInstance
import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.db.DBReference.authFB
import com.micosi.moviesseries.mappers.ApiMovieToListMovieMapper
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State

class MoviesAPIRepository {

    private val apiMovieToListMovieMapper = ApiMovieToListMovieMapper()

    suspend fun getMovies(title: String, type: String): State<List<Movie>> {
        return try {
            val response = RetrofitInstance.retrofit.getMovies(type, title)
            if (response.isSuccessful) {
                val listMovies = apiMovieToListMovieMapper.map(response.body())
                if (listMovies.isNotEmpty()) {
                    State.Success(
                        listMovies
                    )
                } else {
                    State.Error("No results")
                }
            } else {
                State.Error("Connection error")
            }
        } catch (e: Exception) {
            State.Error("Connection error")
        }
    }

    fun addSeriesToDB(movie: Movie, liveData: MutableLiveData<Pair<Boolean, String>>) {
        val path = authFB.currentUser!!.uid + movie.title
        try {
            DBReference.seriesSeenReference.child(path)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value == null) {
                            addSeries(movie, path)
                            liveData.postValue(Pair(true, "The series has been successfully add."))
                        } else {
                            liveData.postValue(Pair(false, "The series is already being seen"))
                        }
                    }
                })
        } catch (e: Exception) {
            liveData.postValue(Pair(false, "The series could not be added"))
        }
    }

    fun addMovieToDB(movie: Movie, liveData: MutableLiveData<Pair<Boolean, String>>) {
        val path = authFB.currentUser!!.uid + movie.title
        try {
            DBReference.moviesSeenReference.child(path)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value == null) {
                            addMovie(movie, path)
                            liveData.postValue(Pair(true, "The movie has been successfully add."))
                        } else {
                            liveData.postValue(Pair(false, "The movie is already being seen"))
                        }
                    }
                })
        } catch (e: Exception) {
            liveData.postValue(Pair(false, "The movie could not be added"))
        }
    }

    private fun addMovie(movie: Movie, path: String) {
        DBReference.moviesUnseenReference.child(path)
            .setValue(movie)
    }

    private fun addSeries(movie: Movie, path: String) {
        DBReference.seriesUnseenReference.child(path)
            .setValue(movie)
    }
}