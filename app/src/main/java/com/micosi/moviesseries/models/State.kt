package com.micosi.moviesseries.models

sealed class State<T> {

    data class Success<T>(val data: T) : State<T>()
    data class Error<T>(val message: String) : State<T>()
}