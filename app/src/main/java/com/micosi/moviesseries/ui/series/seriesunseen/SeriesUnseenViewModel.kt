package com.micosi.moviesseries.ui.series.seriesunseen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State
import com.micosi.moviesseries.repositoris.db.SeriesUnseenRepository
import com.micosi.moviesseries.ui.adapters.MoviesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeriesUnseenViewModel : ViewModel() {

    val moviesAdapter =
        MoviesAdapter({ movie -> addToSeen(movie) }, { movie -> showDeleteDialog(movie) }, "SEEN")

    private val seriesUnseenRepository = SeriesUnseenRepository()

    private val _deleteMovie = MutableLiveData<Movie?>()
    val deleteMovie: MutableLiveData<Movie?>
        get() = _deleteMovie

    init {
        DBReference.seriesUnseenReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                moviesAdapter.addNewItems(seriesUnseenRepository.getData(snapshot))
            }
        })
    }

    private fun addToSeen(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = seriesUnseenRepository.statusChanges(movie)
            if (response is State.Success) {
                Log.d("Test", response.data)
            }
            if (response is State.Error) {
                Log.d("Test", response.message)
            }
        }
    }

    private fun showDeleteDialog(movie: Movie) {
        _deleteMovie.value = movie
        _deleteMovie.value = null
    }

    fun deleteSeries(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = seriesUnseenRepository.deleteMovie(movie)
            if (response is State.Success) {
                Log.d("Test", response.data)
            }
            if (response is State.Error) {
                Log.d("Test", response.message)
            }
        }
    }
}