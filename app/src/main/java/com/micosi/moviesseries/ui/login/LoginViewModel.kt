package com.micosi.moviesseries.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.micosi.moviesseries.db.DBReference.authFB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _logInSuccess = MutableLiveData<String>()
    val loginSuccess: LiveData<String>
        get() = _logInSuccess

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val areInputsNotEmpty = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = email.value?.isNotEmpty() == true && password.value?.isNotEmpty() == true
        }
        addSource(password) {
            value = email.value?.isNotEmpty() == true && password.value?.isNotEmpty() == true
        }
    }

    fun logIn() {
        viewModelScope.launch(Dispatchers.IO) {
            authFB.signInWithEmailAndPassword(email.value!!, password.value!!)
                .addOnSuccessListener {
                    _logInSuccess.postValue("Correct")
                }
                .addOnFailureListener {
                    Log.d("Test", "Fail")
                }
        }
    }

    fun isCurrentUser() = authFB.currentUser != null
}