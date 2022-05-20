package com.micosi.moviesseries.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentRegisterBinding
import com.micosi.moviesseries.ui.extensions.hideKeyboard
import com.micosi.moviesseries.ui.extensions.showSnackBar
import com.micosi.moviesseries.ui.providers.SnackBarProvider

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var snackBarProvider: SnackBarProvider
    private val viewModel = RegisterViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        snackBarProvider = SnackBarProvider(requireActivity())

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@RegisterFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.register.observe(viewLifecycleOwner) { register ->
            requireActivity().hideKeyboard(view)
            if (register.first) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            snackBarProvider.showSnackBar(register.first, register.second)
        }

        binding.toLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}