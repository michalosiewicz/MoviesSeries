package com.micosi.moviesseries.ui.searching.searchingseries

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
import com.micosi.moviesseries.databinding.FragmentSearchingSeriesBinding

class SearchingSeriesFragment : Fragment() {

    private lateinit var binding: FragmentSearchingSeriesBinding
    private val viewModel = SearchingSeriesViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_searching_series,
            container,
            false
        )

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchingSeriesFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.bottomNav.setupWithNavController(findNavController())
        binding.topNav.setupWithNavController(findNavController())
    }

    override fun onStart() {
        super.onStart()

        //binding.bottomNav.selectedItemId = R.id.searchingSeriesFragment
        binding.topNav.selectedItemId = R.id.searchingSeriesFragment
    }
}