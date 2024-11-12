package com.example.fetch.model

interface AppException {
    val description: String
}

class NetworkException: Exception(), AppException {
    override val description = "Network error"
}
