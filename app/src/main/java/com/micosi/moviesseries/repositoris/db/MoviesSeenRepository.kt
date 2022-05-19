package com.micosi.moviesseries.repositoris.db

import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State

class MoviesSeenRepository : DBRepository {

    override fun deleteMovie(movie: Movie): State<String> {
        return try {
            DBReference.moviesSeenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .removeValue()
            State.Success("The movie has been successfully removed.")
        } catch (e: Exception) {
            State.Error("The movie could not be deleted.")
        }
    }

    override fun statusChanges(movie: Movie): State<String> {
        return try {
            DBReference.moviesUnseenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .setValue(movie)
            DBReference.moviesSeenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .removeValue()
            State.Success("The movie has been successfully transfer.")
        } catch (e: Exception) {
            State.Error("The movie could not be transfer.")
        }
    }
}