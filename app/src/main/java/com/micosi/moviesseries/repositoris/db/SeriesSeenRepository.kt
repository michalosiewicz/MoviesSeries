package com.micosi.moviesseries.repositoris.db

import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State

class SeriesSeenRepository : DBRepository {

    override fun deleteMovie(movie: Movie): State<String> {
        return try {
            DBReference.seriesSeenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .removeValue()
            State.Success("Correct addition")
        } catch (e: Exception) {
            State.Error("Connection error")
        }
    }

    override fun statusChanges(movie: Movie): State<String> {
        return try {
            DBReference.seriesUnseenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .setValue(movie)
            DBReference.seriesSeenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .removeValue()
            State.Success("Correct addition")
        } catch (e: Exception) {
            State.Error("Connection error")
        }
    }
}