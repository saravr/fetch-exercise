package com.example.fetch.model

sealed interface Resource<T> {
    class Loading<T>: Resource<T>
    data class Success<T>(val data: T): Resource<T>
    data class Error<T>(val exception: Exception): Resource<T>
}
