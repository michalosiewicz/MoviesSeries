package com.micosi.moviesseries.ui.movies.moviesunseen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentMoviesUnseenBinding
import com.micosi.moviesseries.ui.providers.DeleteDialogProvider

class MoviesUnseenFragment : Fragment() {

    private lateinit var binding: FragmentMoviesUnseenBinding
    private val viewModel = MoviesUnseenViewModel()
    private lateinit var deleteDialog: DeleteDialogProvider


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movies_unseen,
            container,
            false
        )

        deleteDialog = DeleteDialogProvider(requireContext(), "Movie")

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MoviesUnseenFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.deleteMovie.observe(viewLifecycleOwner) { movie ->
            deleteDialog.show({ viewModel.deleteMovie(movie) }, movie.title)
        }

        binding.bottomNav.setupWithNavController(findNavController())
        binding.topNav.setupWithNavController(findNavController())
    }

    override fun onStart() {
        super.onStart()

        binding.bottomNav.selectedItemId = R.id.moviesUnseenFragment
        binding.topNav.selectedItemId = R.id.moviesUnseenFragment
    }
}