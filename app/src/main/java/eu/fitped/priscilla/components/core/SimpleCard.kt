package eu.fitped.priscilla.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SimpleCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    cardText: String,
    disabled: Boolean = false,
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .clickable(
                enabled = onClick != null && !disabled,
                onClick = onClick ?: {}
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            disabledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
        )
    ) {
        Text(
            text = cardText,
            color = if (disabled)
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            else
                MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
            content()
        }
    }
}