package com.micosi.moviesseries.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.micosi.moviesseries.db.DBReference.authFB

class ProfileViewModel : ViewModel() {

    val email = MutableLiveData("")

    private val _logOut = MutableLiveData<String>()
    val logOut: LiveData<String>
        get() = _logOut

    init {
        email.value = "Email: " + authFB.currentUser?.email
    }

    fun logOut() {
        Firebase.auth.signOut()
        _logOut.value = "LogOut"
    }
}