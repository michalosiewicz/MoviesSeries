package com.micosi.moviesseries.db

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object DBReference {

    private val fireBase = FirebaseDatabase.getInstance()

    val authFB = FirebaseAuth.getInstance()
    val seriesUnseenReference = fireBase.getReference("SeriesUnseen")
    val seriesSeenReference = fireBase.getReference("SeriesSeen")
    val moviesUnseenReference = fireBase.getReference("MoviesUnseen")
    val moviesSeenReference = fireBase.getReference("MoviesSeen")
}