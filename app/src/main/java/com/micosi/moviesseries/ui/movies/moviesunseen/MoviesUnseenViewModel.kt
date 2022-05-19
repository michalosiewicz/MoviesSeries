package com.micosi.moviesseries.ui.movies.moviesunseen

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
        MoviesAdapter(
            { movie -> addToSeen(movie) },
            { movie -> showDeleteDialog(movie) },
            "SEEN"
        )

    private val moviesUnseenRepository = MoviesUnseenRepository()

    private val _showDialog = MutableLiveData<Movie>()
    val showDialog: LiveData<Movie>
        get() = _showDialog

    private val _showSnackBar = MutableLiveData<Pair<Boolean, String>>()
    val showSnackBar: MutableLiveData<Pair<Boolean, String>>
        get() = _showSnackBar

    init {
        getData()
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            handleState(moviesUnseenRepository.deleteMovie(movie))
        }
    }

    private fun getData() {
        DBReference.moviesUnseenReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                moviesAdapter.addNewItems(moviesUnseenRepository.getData(snapshot))
            }
        })
    }

    private fun addToSeen(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            handleState(moviesUnseenRepository.statusChanges(movie))
        }
    }

    private fun showDeleteDialog(movie: Movie) {
        _showDialog.value = movie
    }

    private fun handleState(state: State<String>) {
        if (state is State.Success) {
            _showSnackBar.postValue(Pair(true, state.data))
        }
        if (state is State.Error) {
            _showSnackBar.postValue(Pair(false, state.message))
        }
    }
}