package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.fitped.priscilla.components.HTMLTextWithDND
import eu.fitped.priscilla.model.TaskContentDND

@Composable
fun DNDTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContentDND,
    activityType: String,
    taskId: Long,
    taskTypeId: Long
) {
    Column(
        modifier = modifier
    ) {
        HTMLTextWithDND(html = taskContent.content, fakes = taskContent.fakes)
    }
}