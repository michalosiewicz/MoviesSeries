package com.micosi.moviesseries.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.micosi.moviesseries.db.DBReference.authFB

class ProfileViewModel : ViewModel() {

    val email = MutableLiveData("")

    init {
        email.value = "Email: " + authFB.currentUser?.email
    }
}