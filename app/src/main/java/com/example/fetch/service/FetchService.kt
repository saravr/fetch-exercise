package com.example.fetch.service

import com.example.fetch.model.Item
import retrofit2.http.GET

interface FetchService {
    @GET("/hiring.json")
    suspend fun getItems(): List<Item>
}
