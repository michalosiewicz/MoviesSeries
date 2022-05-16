package com.micosi.moviesseries.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel = ProfileViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ProfileFragment.viewModel
        }.root
    }
}