package com.example.fetch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch.model.Resource
import com.example.fetch.ui.model.ItemView
import com.example.fetch.usecase.FetchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchViewModel @Inject constructor(
    private val fetchUseCase: FetchUseCase,
): ViewModel() {
    private val _itemList = MutableStateFlow<Resource<List<ItemView>>>(Resource.Success(emptyList()))
    val itemList = _itemList.asStateFlow()

    init {
        viewModelScope.launch {
            fetchUseCase.groupedItems.collectLatest {
                val output = mutableListOf<ItemView>()
                it.forEach { (listId, items) ->
                    output.add(ItemView.GroupHeader(listId))
                    output.addAll(items.map { item -> ItemView.DataItem(item) })
                }
                _itemList.emit(Resource.Success(output))
            }
        }
    }

    fun getItems() {
        viewModelScope.launch {
            _itemList.emit(Resource.Loading())
            try {
                fetchUseCase.getGroupedItems()
            } catch (exception: Exception) {
                _itemList.emit(Resource.Error(exception))
            }
        }
    }
}
