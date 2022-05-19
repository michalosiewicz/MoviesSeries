package com.micosi.moviesseries.ui.searching.searchingmovies

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micosi.moviesseries.models.Movie
import com.micosi.moviesseries.models.State
import com.micosi.moviesseries.repositoris.api.MoviesAPIRepository
import com.micosi.moviesseries.ui.adapters.MoviesApiAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchingMoviesViewModel : ViewModel() {

    private val moviesAPIRepository = MoviesAPIRepository()

    private val _showSnackBar = MutableLiveData<Pair<Boolean, String>>()
    val showSnackBar: MutableLiveData<Pair<Boolean, String>>
        get() = _showSnackBar

    val moviesAdapter = MoviesApiAdapter { movie -> addMoviesToDB(movie) }

    val title = MutableLiveData("")

    val isTitleNotEmpty = MediatorLiveData<Boolean>().apply {
        addSource(title) { title ->
            value = title.isNotEmpty()
        }
    }

    fun getMovies() {
        val query = title.value!!
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies = moviesAPIRepository.getMovies(query, "movies")
            if (listMovies is State.Success) {
                withContext(Dispatchers.Main) {
                    moviesAdapter.addNewItems(listMovies.data)
                }
            }
        }
    }

    private fun addMoviesToDB(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            handleState(moviesAPIRepository.addMovieToDB(movie))
        }
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