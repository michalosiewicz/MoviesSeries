package com.micosi.moviesseries.ui.searching.searchingmovies

import android.view.View
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
    val visible = MutableLiveData(View.GONE)

    val isTitleNotEmpty = MediatorLiveData<Boolean>().apply {
        addSource(title) { title ->
            value = title.isNotEmpty() && visible.value == View.GONE
        }
        addSource(visible) { visible ->
            value = title.value!!.isNotEmpty() && visible == View.GONE
        }
    }

    fun getMovies() {
        visible.value = View.VISIBLE
        val query = title.value!!
        viewModelScope.launch(Dispatchers.IO) {
            val state = moviesAPIRepository.getMovies(query, "movies")
            if (state is State.Success) {
                withContext(Dispatchers.Main) {
                    moviesAdapter.addNewItems(state.data)
                }
                visible.postValue(View.GONE)
            }
            if (state is State.Error) {
                withContext(Dispatchers.Main) {
                    moviesAdapter.addNewItems(emptyList())
                }
                _showSnackBar.postValue(Pair(false, state.message))
                visible.postValue(View.GONE)
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