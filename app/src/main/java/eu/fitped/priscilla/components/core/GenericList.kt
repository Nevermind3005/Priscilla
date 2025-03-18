package eu.fitped.priscilla.components.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.components.ScreenHeader

@Composable
fun <T> GenericList(
    modifier: Modifier = Modifier,
    headerText: String,
    items: List<T>,
    itemKey: (T) -> Any,
    itemContent: @Composable (T) -> Unit
) {
    val listState = rememberLazyListState()

    ScreenHeader(
        listState = listState,
        headerText = headerText,
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(padding)
                .fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(
                count = items.size,
                key = { index ->
                    itemKey(items[index])
                },
                itemContent = { index ->
                    itemContent(items[index])
                }
            )
        }
    }
}