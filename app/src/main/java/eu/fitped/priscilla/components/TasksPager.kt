package eu.fitped.priscilla.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.tasks.CheckboxTask
import eu.fitped.priscilla.components.tasks.DNDTask
import eu.fitped.priscilla.components.tasks.DraggableItemsTask
import eu.fitped.priscilla.components.tasks.InlineTextTask
import eu.fitped.priscilla.components.tasks.RadioTask
import eu.fitped.priscilla.components.tasks.TextTask
import eu.fitped.priscilla.model.TaskContent
import eu.fitped.priscilla.model.TaskContentDND
import eu.fitped.priscilla.model.TaskContentText
import eu.fitped.priscilla.model.TaskContentDraggable
import eu.fitped.priscilla.model.TaskDto
import eu.fitped.priscilla.model.TaskType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksPager(
    modifier: Modifier = Modifier,
    taskList: List<TaskDto>,
    pagerState: PagerState,
    score: MutableState<StarRatingData>,
    onClick: MutableState<() -> Unit>
) {
    val mapper = jacksonObjectMapper()

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        verticalAlignment = Alignment.Top,
        userScrollEnabled = false
    ) { page ->
        val currentTask = taskList[page]
        if (currentTask.maxScore == 0) {
            score.value = StarRatingData(score = 0, maxScore = 100)
        } else {
            score.value = StarRatingData(score = currentTask.score, maxScore = currentTask.maxScore)
        }
        when(TaskType.forId(taskList[page].taskTypeId)) {
            TaskType.TEXT_ONLY -> {
                HTMLText(html = currentTask.content)
                onClick.value = {}
            }
            TaskType.TEXT_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    TextTask(
                        taskContent = taskContent,
                        taskId = currentTask.taskId,
                        activityType = "chapter",
                        taskTypeId = currentTask.taskTypeId,
                        onClick = onClick
                    )
                }
            }
            TaskType.RADIO_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    RadioTask(
                        taskContent = taskContent,
                        taskId = currentTask.taskId,
                        activityType = "chapter",
                        taskTypeId = currentTask.taskTypeId,
                        onClick = onClick
                    )
                }
            }
            TaskType.CHECKBOX_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    CheckboxTask(
                        taskContent = taskContent,
                        taskId = currentTask.taskId,
                        activityType = "chapter",
                        taskTypeId = currentTask.taskTypeId,
                        onClick = onClick
                    )
                }
            }
            TaskType.TEXT_INSIDE_CONTENT -> {
                val taskContent: TaskContentText = mapper.readValue(currentTask.content)
                InlineTextTask(
                    modifier = Modifier.fillMaxWidth(),
                    taskContent = taskContent,
                    activityType = "chapter",
                    taskId = currentTask.taskId,
                    taskTypeId = currentTask.taskTypeId,
                    onClick = onClick
                )
            }
            TaskType.DND -> {
                val taskContent: TaskContentDND = mapper.readValue(currentTask.content)
                DNDTask(
                    taskContent = taskContent,
                    activityType = "chapter",
                    taskId = currentTask.taskId,
                    taskTypeId = currentTask.taskTypeId
                )
            }
            TaskType.DRAGGABLE -> {
                val taskContent: TaskContentDraggable = mapper.readValue(currentTask.content)
                DraggableItemsTask(
                    taskContent = taskContent,
                    activityType = "chapter",
                    taskId = currentTask.taskId,
                    taskTypeId = currentTask.taskTypeId,
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            TaskType.CODE -> {
                Text(text = "CODE -> Not implemented")
            }
            else -> {
                Text(text = "Error")
            }
        }
    }
}