package com.example.fetch.repository

import com.example.fetch.model.Item
import com.example.fetch.model.NetworkException
import com.example.fetch.service.FetchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface FetchRepository {
    val itemList: SharedFlow<List<Item>>

    suspend fun getItems()
}

class FetchRepositoryImpl @Inject constructor(
    private val fetchService: FetchService,
): FetchRepository {
    private val _itemList = MutableSharedFlow<List<Item>>()
    override val itemList = _itemList.asSharedFlow()

    override suspend fun getItems() {
        try {
            val items = withContext(Dispatchers.IO) { fetchService.getItems() }
            _itemList.emit(items)
        } catch (exception: Exception) {
            throw NetworkException()
        }
    }
}
