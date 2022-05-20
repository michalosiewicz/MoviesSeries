package com.micosi.moviesseries.ui.searching.searchingseries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentSearchingSeriesBinding
import com.micosi.moviesseries.ui.extensions.hideKeyboard
import com.micosi.moviesseries.ui.extensions.showSnackBar
import com.micosi.moviesseries.ui.providers.SnackBarProvider

class SearchingSeriesFragment : Fragment() {

    private lateinit var binding: FragmentSearchingSeriesBinding
    private lateinit var snackBarProvider: SnackBarProvider
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

        snackBarProvider = SnackBarProvider(requireActivity())

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchingSeriesFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showSnackBar.observe(viewLifecycleOwner) { values ->
            snackBarProvider.showSnackBar(values.first, values.second)
        }

        viewModel.findSuccess.observe(viewLifecycleOwner) {
            requireActivity().hideKeyboard(view)
        }

        binding.topNav.setupWithNavController(findNavController())
    }

    override fun onStart() {
        super.onStart()

        binding.topNav.selectedItemId = R.id.searchingSeriesFragment
    }
}