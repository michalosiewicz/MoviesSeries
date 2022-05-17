package com.micosi.moviesseries.ui.series.seriesseen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentSeriesSeenBinding
import com.micosi.moviesseries.ui.providers.DeleteMovieDialogProvider

class SeriesSeenFragment : Fragment() {

    private lateinit var binding: FragmentSeriesSeenBinding
    private val viewModel = SeriesSeenViewModel()
    private lateinit var deleteMovieDialog: DeleteMovieDialogProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_series_seen,
            container,
            false
        )

        deleteMovieDialog = DeleteMovieDialogProvider(requireContext(), "Series")

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SeriesSeenFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.deleteMovie.observe(viewLifecycleOwner) { movie ->
            deleteMovieDialog.show({ viewModel.deleteSeries(movie) }, movie.title)
        }

        binding.topNav.setupWithNavController(findNavController())
    }

    override fun onStart() {
        super.onStart()

        binding.topNav.selectedItemId = R.id.seriesSeenFragment
    }
}