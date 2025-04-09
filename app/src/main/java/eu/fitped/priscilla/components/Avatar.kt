package eu.fitped.priscilla.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Avatar(
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            .circleLayout()
            .padding(24.dp)

    )
}

fun Modifier.circleLayout() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        val currentHeight = placeable.height
        val currentWidth = placeable.width
        val newDiameter = maxOf(currentHeight, currentWidth)

        layout(newDiameter, newDiameter) {
            placeable.placeRelative((newDiameter-currentWidth)/2, (newDiameter-currentHeight)/2)
        }
    }