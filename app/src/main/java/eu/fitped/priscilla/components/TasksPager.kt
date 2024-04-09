package eu.fitped.priscilla.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.tasks.CheckboxTask
import eu.fitped.priscilla.components.tasks.RadioTask
import eu.fitped.priscilla.components.tasks.TextTask
import eu.fitped.priscilla.model.TaskContent
import eu.fitped.priscilla.model.TaskContentText
import eu.fitped.priscilla.model.TaskDto
import eu.fitped.priscilla.model.TaskType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksPager(
    modifier: Modifier = Modifier,
    taskList: List<TaskDto>,
    pagerState: PagerState
) {
    var answer by remember { mutableStateOf("") }

    val mapper = jacksonObjectMapper()
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        verticalAlignment = Alignment.Top,
        userScrollEnabled = false
    ) { page ->
        val currentTask = taskList[page]
        when(TaskType.forId(taskList[page].taskTypeId)) {
            TaskType.TEXT_ONLY -> HTMLText(html = currentTask.content)
            TaskType.TEXT_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    StarRating(score = currentTask.score, maxScore = currentTask.maxScore)
                    TextTask(
                        taskContent = taskContent,
                        taskId = currentTask.taskId,
                        activityType = "chapter",
                        taskTypeId = currentTask.taskTypeId
                    )
                }
            }
            TaskType.RADIO_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    StarRating(score = currentTask.score, maxScore = currentTask.maxScore)
                    RadioTask(
                        taskContent = taskContent,
                        taskId = currentTask.taskId,
                        activityType = "chapter",
                        taskTypeId = currentTask.taskTypeId
                    )
                }
            }
            TaskType.CHECKBOX_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    StarRating(score = currentTask.score, maxScore = currentTask.maxScore)
                    CheckboxTask(
                        taskContent = taskContent,
                        taskId = currentTask.taskId,
                        activityType = "chapter",
                        taskTypeId = currentTask.taskTypeId
                    )
                }
            }
            TaskType.TEXT_INSIDE_ONE -> {
                val taskContent: TaskContentText = mapper.readValue(currentTask.content)
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HTMLTextWithEditText(html = taskContent.content, editTextValue = "", onEditTextValueChanged = {
                        answer = it
                    })
                }
            }
            else -> Text(text = "Error ${taskList[page].taskTypeId}")
        }
    }
}