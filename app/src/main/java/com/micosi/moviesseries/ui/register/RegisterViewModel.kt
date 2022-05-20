package com.micosi.moviesseries.ui.register

import androidx.lifecycle.*
import com.micosi.moviesseries.db.DBReference.authFB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val _register = MutableLiveData<Pair<Boolean, String>>()
    val register: LiveData<Pair<Boolean, String>>
        get() = _register

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
                    _register.postValue(Pair(true, "Registration was successful"))
                }
                .addOnFailureListener {
                    _register.postValue(Pair(false, "Incorrect data"))
                }
        }
    }
}