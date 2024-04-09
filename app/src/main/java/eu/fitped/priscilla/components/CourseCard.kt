package eu.fitped.priscilla.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.example.compose.PriscillaTheme
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.CourseDto

@Composable
fun CourseCard(
    modifier: Modifier = Modifier,
    courseDto: CourseDto,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = onClick ?: {} ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )
    ) {
        Text(
            text = courseDto.name,
            color = MaterialTheme.colorScheme.onSurface,
//            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            )
        Text(
            text = courseDto.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
            )
        {
            ScoreIcon(
                count = courseDto.contentCount,
                passed = courseDto.contentPassed,
                iconPainter = painterResource(R.drawable.article_24px)
            )
            ScoreIcon(
                count = courseDto.taskCount,
                passed = courseDto.taskPassed,
                iconPainter = painterResource(R.drawable.quiz_24px),
            )
            ScoreIcon(
                count = courseDto.programCount,
                passed = courseDto.programPassed,
                iconPainter = painterResource(R.drawable.code_24px)
            )
        }
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = courseDto.progress / 100.0f
        )
    }
}

@Composable
fun ScoreIcon(
    modifier: Modifier = Modifier,
    count: String,
    passed: String,
    iconPainter: Painter
) {
    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        ) {
        Icon(
            painter = iconPainter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(text = "${passed}/${count}")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
@Composable
fun CourseCardPreview() {
    val courseDto = CourseDto(
        "#EF5350",
        1,
        "Java - základy jazyka",
        "Základné údajové a programové štruktúry jazyka",
        "5278",
        "9590",
        "535",
        609,
        "170",
        "245",
        "194",
        "170",
        "171",
        "194",
        "1934",
        "3344",
        "1940",
        "7650",
        88
    )
    PriscillaTheme {
        Surface {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CourseCard(courseDto = courseDto)
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun ScoreIconPreview() {
    ScoreIcon(
        iconPainter = painterResource(R.drawable.article_24px),
        count = "4",
        passed = "10"
    )
}