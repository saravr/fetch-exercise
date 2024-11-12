package com.example.fetch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetch.R
import com.example.fetch.model.Item
import com.example.fetch.model.Resource
import com.example.fetch.ui.model.ItemView

private val HORIZONTAL_PADDING = 12.dp

@Composable
fun ItemsScreen(
    items: Resource<List<ItemView>>,
    getItems: () -> Unit,
    paddingValues: PaddingValues,
) {
    var collapsed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        getItems()
    }

    when (items) {
        is Resource.Loading -> {
            LoadingScreen()
        }
        is Resource.Error -> {
            ErrorScreen(items.exception)
        }
        is Resource.Success -> {
            Column(modifier = Modifier.padding(paddingValues)) {
                ListHeader(
                    stringResource(R.string.items),
                    collapsed = collapsed,
                    setCollapsed = {
                        collapsed = it
                    }
                )

                HorizontalDivider()

                ItemsList(
                    itemViews = items.data,
                    collapsed = collapsed,
                )
            }
        }
    }
}

@Composable
fun ListHeader(
    title: String,
    collapsed: Boolean,
    setCollapsed: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = HORIZONTAL_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        IconButton(
            onClick = {
                setCollapsed(!collapsed)
            }
        ) {
            if (collapsed) {
                Icon(
                    Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Expand"
                )
            } else {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Collapse"
                )
            }
        }
    }
}

@Composable
fun ItemsList(
    itemViews: List<ItemView>,
    collapsed: Boolean,
) {
    val selectedItemViews = if (!collapsed) {
        itemViews
    } else {
        itemViews.filterIsInstance<ItemView.GroupHeader>()
    }
    LazyColumn {
        items(selectedItemViews) { itemView ->
            ItemCell(
                itemView = itemView,
                collapsed = collapsed,
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING))
        }
    }
}

@Composable
fun ItemCell(
    itemView: ItemView,
    collapsed: Boolean,
) {
    when (itemView) {
        is ItemView.GroupHeader -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(vertical = 8.dp),
            ) {
                Text(
                    stringResource(R.string.list, itemView.listId),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING)
                )
            }
        }

        is ItemView.DataItem -> {
            if (!collapsed) {
                val item = itemView.item
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = HORIZONTAL_PADDING),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(stringResource(R.string.id, item.id))
                        Text(stringResource(R.string.listid, item.listId))
                    }
                    Text(item.name.toString(), style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemsScreen() {
    val items = listOf(
        ItemView.GroupHeader(100),
        ItemView.DataItem(Item(10, 100, "Item 10")),
        ItemView.DataItem(Item(20, 100, "Item 20")),
        ItemView.DataItem(Item(20, 100, "Item 30")),
        ItemView.GroupHeader(200),
        ItemView.DataItem(Item(70, 200, "Item 70")),
        ItemView.DataItem(Item(80, 200, "Item 80")),
    )

    ItemsScreen(items = Resource.Success(items), getItems = {}, paddingValues = PaddingValues(8.dp))
}
