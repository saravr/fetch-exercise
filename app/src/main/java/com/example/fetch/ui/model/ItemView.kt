package com.example.fetch.ui.model

import com.example.fetch.model.Item

// defines different view holders to show list of items
sealed interface ItemView {
    data class GroupHeader(val listId: Int): ItemView
    data class DataItem(val item: Item): ItemView
}
