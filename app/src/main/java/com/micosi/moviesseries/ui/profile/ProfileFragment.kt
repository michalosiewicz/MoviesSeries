package com.micosi.moviesseries.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.micosi.moviesseries.R
import com.micosi.moviesseries.databinding.FragmentProfileBinding
import com.micosi.moviesseries.ui.activities.RegisterActivity
import com.micosi.moviesseries.ui.providers.LogOutDialogProvider

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var logOutDialogProvider: LogOutDialogProvider
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

        logOutDialogProvider = LogOutDialogProvider(requireContext())

        return binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ProfileFragment.viewModel
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOut.setOnClickListener {
            logOutDialogProvider.show { startRegisterActivity() }
        }
    }

    private fun startRegisterActivity() {
        Firebase.auth.signOut()
        val intent = Intent(context, RegisterActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }
}