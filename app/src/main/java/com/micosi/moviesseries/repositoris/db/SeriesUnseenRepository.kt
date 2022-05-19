package com.micosi.moviesseries.repositoris.db

import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State

class SeriesUnseenRepository : DBRepository {

    override fun deleteMovie(movie: Movie): State<String> {
        return try {
            DBReference.seriesUnseenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .removeValue()
            State.Success("The series has been successfully removed.")
        } catch (e: Exception) {
            State.Error("The series could not be deleted.")
        }
    }

    override fun statusChanges(movie: Movie): State<String> {
        return try {
            DBReference.seriesSeenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .setValue(movie)
            DBReference.seriesUnseenReference.child(DBReference.authFB.currentUser!!.uid + movie.title)
                .removeValue()
            State.Success("The series has been successfully transfer.")
        } catch (e: Exception) {
            State.Error("The series could not be transfer.")
        }
    }
}