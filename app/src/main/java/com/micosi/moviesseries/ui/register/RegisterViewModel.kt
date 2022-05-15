package com.micosi.moviesseries.ui.register

import android.util.Log
import androidx.lifecycle.*
import com.micosi.moviesseries.db.DBReference.authFB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val _registerInSuccess = MutableLiveData<String>()
    val registerSuccess: LiveData<String>
        get() = _registerInSuccess

    val areInputsNotEmpty = MediatorLiveData<Boolean>().apply {
        addSource(email) {
            value = email.value?.isNotEmpty() == true && password.value?.isNotEmpty() == true
        }
        addSource(password) {
            value = email.value?.isNotEmpty() == true && password.value?.isNotEmpty() == true
        }
    }

    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            authFB.createUserWithEmailAndPassword(email.value!!, password.value!!)
                .addOnSuccessListener {
                    _registerInSuccess.postValue("Correct")
                }
                .addOnFailureListener {
                    Log.d("Test", "Fail")
                }
        }
    }
}