package eu.fitped.priscilla.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.model.LessonDetail

@Composable
fun LessonCard(
    modifier: Modifier = Modifier,
    lessonDetail: LessonDetail,
    onClick: (() -> Unit)? = null
) {
    val taskCount = lessonDetail.tasksNotFinished + lessonDetail.tasksFinished + lessonDetail.programsNotFinished + lessonDetail.programsFinished
    val completedTasksCount = lessonDetail.tasksFinished + lessonDetail.programsFinished
    var lessonProgress = 100
    if (taskCount > 0) {
        lessonProgress = 100 / taskCount * completedTasksCount
    }
    Card(
        modifier = modifier.fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = onClick ?: {} ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
        )
    ) {
        Text(
            text = lessonDetail.lessonName,
            color = MaterialTheme.colorScheme.onSurface,
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
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = lessonProgress / 100.0f
            )
        }
    }
}