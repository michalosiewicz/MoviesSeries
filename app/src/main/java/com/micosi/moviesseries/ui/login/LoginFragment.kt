package com.micosi.moviesseries.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentLoginBinding
import com.micosi.moviesseries.ui.activities.MainActivity
import com.micosi.moviesseries.ui.extensions.hideKeyboard
import com.micosi.moviesseries.ui.extensions.showSnackBar
import com.micosi.moviesseries.ui.providers.SnackBarProvider

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var snackBarProvider: SnackBarProvider
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

        snackBarProvider = SnackBarProvider(requireActivity())

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@LoginFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.login.observe(viewLifecycleOwner) { values ->
            requireActivity().hideKeyboard(view)
            if (values.first) {
                navigateToApp()
            } else {
                snackBarProvider.showError(values.second)
            }
        }

        binding.toRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun navigateToApp() {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}