package com.micosi.moviesseries.ui.login

import androidx.lifecycle.*
import com.micosi.moviesseries.db.DBReference.authFB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _logIn = MutableLiveData<Pair<Boolean, String>>()
    val login: LiveData<Pair<Boolean, String>>
        get() = _logIn

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
                    _logIn.postValue(Pair(true, "Login was successful"))
                }
                .addOnFailureListener {
                    _logIn.postValue(Pair(false, "Incorrect data"))
                }
        }
    }
}