package com.example.fetch.usecase

import com.example.fetch.model.Item
import com.example.fetch.repository.FetchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.SortedMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface FetchUseCase {
    val groupedItems: Flow<SortedMap<Int, List<Item>>>

    suspend fun getGroupedItems()
}

class FetchUseCaseImpl @Inject constructor(
    private val fetchRepository: FetchRepository,
): FetchUseCase {
    override val groupedItems = fetchRepository.itemList.map { items ->
        items
            .asSequence() // performance benefit from lazy evaluation
            .filter { !it.name.isNullOrBlank() }
            .sortedBy { it.name }
            .groupBy { it.listId }
            .toSortedMap()
    }.flowOn(Dispatchers.Default)

    override suspend fun getGroupedItems() {
        fetchRepository.getItems()
    }
}
