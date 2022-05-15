package com.micosi.moviesseries.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.micosi.moviesseries.databinding.ItemMovieApiBinding
import com.micosi.moviesseries.models.Movie
import com.squareup.picasso.Picasso

class MoviesApiAdapter(
    val addMovie: (Movie) -> Unit,
) : RecyclerView.Adapter<MoviesApiAdapter.ViewHolder>() {

    private val listMovies = mutableListOf<Movie>()

    @SuppressLint("NotifyDataSetChanged")
    fun addNewItems(movies: List<Movie>) {
        listMovies.clear()
        listMovies.addAll(movies)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemMovieApiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {
            binding.title.text = movie.title
            binding.year.text = "Year : ${movie.year}"
            Picasso.get().load(movie.image).into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieApiBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovies[position])

        holder.binding.button.setOnClickListener {
            addMovie(listMovies[position])
        }
    }

    override fun getItemCount(): Int = listMovies.size
}