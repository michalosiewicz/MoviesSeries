package com.micosi.moviesseries.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.micosi.moviesseries.databinding.ItemMovieBinding
import com.micosi.moviesseries.models.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter(
    val addMovie: (Movie) -> Unit,
    val deleteMovie: (Movie) -> Unit,
    private val buttonText: String
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val listMovies = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun addNewItems(movies: List<Movie>) {
        listMovies.clear()
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie, buttonText: String) {
            binding.title.text = movie.title
            binding.year.text = "Year : ${movie.year}"
            binding.button.text = buttonText
            Picasso.get().load(movie.image).into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovies[position], buttonText)

        holder.binding.button.setOnClickListener {
            addMovie(listMovies[position])
        }

        holder.binding.delete.setOnClickListener {
            deleteMovie(listMovies[position])
        }
    }

    override fun getItemCount(): Int = listMovies.size
}