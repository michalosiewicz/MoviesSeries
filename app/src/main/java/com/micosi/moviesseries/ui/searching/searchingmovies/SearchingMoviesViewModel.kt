package com.micosi.moviesseries.ui.searching.searchingmovies

import android.util.Log
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
            val response = moviesAPIRepository.addMovieToDB(movie)
            if (response is State.Success) {
                Log.d("Test", response.data)
            }
            if (response is State.Error) {
                Log.d("Test", response.message)
            }
        }
    }
}