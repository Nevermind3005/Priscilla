package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.fitped.priscilla.components.core.SimpleCard
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
    SimpleCard(
        cardText = lessonDetail.lessonName,
        onClick = onClick,
    ) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = lessonProgress / 100.0f
        )
    }

}