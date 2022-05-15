package com.micosi.moviesseries.repositoris.db

import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State

interface DBRepository {

    fun deleteMovie(movie: Movie): State<String>

    fun statusChanges(movie: Movie): State<String>
}