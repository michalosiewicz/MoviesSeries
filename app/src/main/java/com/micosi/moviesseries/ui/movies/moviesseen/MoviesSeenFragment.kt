package com.micosi.moviesseries.ui.movies.moviesseen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentMoviesSeenBinding
import com.micosi.moviesseries.ui.extensions.showSnackBar
import com.micosi.moviesseries.ui.providers.DeleteMovieDialogProvider
import com.micosi.moviesseries.ui.providers.SnackBarProvider

class MoviesSeenFragment : Fragment() {

    private lateinit var binding: FragmentMoviesSeenBinding
    private val viewModel = MoviesSeenViewModel()
    private lateinit var deleteMovieDialog: DeleteMovieDialogProvider
    private lateinit var snackBarProvider: SnackBarProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movies_seen,
            container,
            false
        )

        deleteMovieDialog = DeleteMovieDialogProvider(
            requireContext(),
            "Movie"
        )
        snackBarProvider = SnackBarProvider(requireActivity())

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MoviesSeenFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showDialog.observe(viewLifecycleOwner) { movie ->
            deleteMovieDialog.show({ viewModel.deleteMovie(movie) }, movie.title)
        }

        viewModel.showSnackBar.observe(viewLifecycleOwner) { values ->
            snackBarProvider.showSnackBar(values.first, values.second)
        }

        binding.topNav.setupWithNavController(findNavController())
    }

    override fun onStart() {
        super.onStart()

        binding.topNav.selectedItemId = R.id.moviesSeenFragment
    }
}