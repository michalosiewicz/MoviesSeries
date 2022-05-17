package com.micosi.moviesseries.repositoris.db

import com.google.firebase.database.DataSnapshot
import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.db.DBReference.authFB
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State

interface DBRepository {

    fun deleteMovie(movie: Movie): State<String>

    fun statusChanges(movie: Movie): State<String>

    fun getData(snapshot: DataSnapshot): List<Movie>{

        val listMovies = mutableListOf<Movie>()
        for (row in snapshot.children) {
            val movie = row.getValue(Movie::class.java)!!
            if (movie.userUID == authFB.uid) {
                listMovies.add(row.getValue(Movie::class.java)!!)
            }
        }
        return listMovies
    }
}