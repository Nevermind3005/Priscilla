package eu.fitped.priscilla.components

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ScreenHeader(
    listState: LazyListState,
    @StringRes headerResId: Int,
    content: @Composable (PaddingValues) -> Unit
) {
    val isCompactHeader = remember { derivedStateOf { listState.firstVisibleItemScrollOffset > 5 || listState.firstVisibleItemIndex > 0 } }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)),
            ) {
                AnimatedVisibility(visible = !isCompactHeader.value) {
                    FullHeader(text = stringResource(headerResId))
                }
                AnimatedVisibility(visible = isCompactHeader.value) {
                    CompactHeader(text = stringResource(headerResId))
                }
            }
        }
    ) { padding ->
        content(padding)
    }
}