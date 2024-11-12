package com.example.fetch.ui.model

import com.example.fetch.model.Item

sealed interface ItemView {
    data class GroupHeader(val header: String): ItemView
    data class DataItem(val item: Item): ItemView
}
