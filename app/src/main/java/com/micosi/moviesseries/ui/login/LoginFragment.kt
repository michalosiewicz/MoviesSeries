package com.micosi.moviesseries.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel = LoginViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LoginFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            navigateToApp()
        }

        binding.toRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.isCurrentUser()) {
            navigateToApp()
        }
    }

    private fun navigateToApp() {
        //findNavController().navigate(R.id.action_logInFragment_to_seriesUnseenFragment)
    }
}