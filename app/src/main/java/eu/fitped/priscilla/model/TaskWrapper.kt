package eu.fitped.priscilla.model

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TaskWrapper(
    modifier: Modifier = Modifier,
    header: @Composable() () -> Unit,
    content: @Composable() () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        header()
        content()
    }
}