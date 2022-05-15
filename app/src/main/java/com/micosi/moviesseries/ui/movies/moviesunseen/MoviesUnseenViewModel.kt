package com.micosi.moviesseries.ui.movies.moviesunseen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.micosi.moviesseries.db.DBReference
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State
import com.micosi.moviesseries.repositoris.db.MoviesUnseenRepository
import com.micosi.moviesseries.ui.adapters.MoviesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesUnseenViewModel : ViewModel() {

    val moviesAdapter =
        MoviesAdapter({ movie -> addToSeen(movie) }, { movie -> showDeleteDialog(movie) }, "SEEN")

    private val moviesUnseenRepository = MoviesUnseenRepository()

    private val _deleteMovie = MutableLiveData<Movie>()
    val deleteMovie: LiveData<Movie>
        get() = _deleteMovie

    init {
        DBReference.moviesUnseenReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val listMovies = mutableListOf<Movie>()
                for (row in snapshot.children) {
                    listMovies.add(row.getValue(Movie::class.java)!!)
                }
                moviesAdapter.addNewItems(listMovies)
            }
        })
    }

    private fun addToSeen(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = moviesUnseenRepository.statusChanges(movie)
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
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = moviesUnseenRepository.deleteMovie(movie)
            if (response is State.Success) {
                Log.d("Test", response.data)
            }
            if (response is State.Error) {
                Log.d("Test", response.message)
            }
        }
    }
}