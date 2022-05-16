package com.micosi.moviesseries.ui.searching.searchingmovies

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentSearchingMoviesBinding

class SearchingMoviesFragment : Fragment() {

    private lateinit var binding: FragmentSearchingMoviesBinding
    private val viewModel = SearchingMoviesViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_searching_movies,
            container,
            false
        )

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchingMoviesFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // binding.bottomNav.setupWithNavController(findNavController())
        binding.topNav.setupWithNavController(findNavController())
    }

    override fun onStart() {
        super.onStart()

       // binding.bottomNav.selectedItemId = R.id.searchingSeriesFragment
        binding.topNav.selectedItemId = R.id.searchingMoviesFragment
    }
}