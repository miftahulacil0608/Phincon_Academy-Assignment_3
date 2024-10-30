package com.example.mytraver.presentation

sealed class StateUser<out T> {
    data object Loading : StateUser<Nothing>()
    data class Success<out T>(val data: T) : StateUser<T>()
    data class Error(val error: String) : StateUser<Nothing>()
}