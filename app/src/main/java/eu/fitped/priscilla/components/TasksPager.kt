package eu.fitped.priscilla.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.tasks.CheckboxTask
import eu.fitped.priscilla.components.tasks.RadioTask
import eu.fitped.priscilla.components.tasks.TextTask
import eu.fitped.priscilla.model.TaskContent
import eu.fitped.priscilla.model.TaskDto
import eu.fitped.priscilla.model.TaskType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TasksPager(
    modifier: Modifier = Modifier,
    taskList: List<TaskDto>
) {
    val tasksPagerStat = rememberPagerState(pageCount = {
        taskList.size
    })
    val mapper = jacksonObjectMapper()
    HorizontalPager(
        modifier = modifier,
        state = tasksPagerStat,
        verticalAlignment = Alignment.Top
    ) { page ->
        val currentTask = taskList[page]
        when(TaskType.forId(taskList[page].taskTypeId)) {
            TaskType.TEXT_ONLY -> HTMLText(html = currentTask.content)
            TaskType.TEXT_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    StarRating(score = currentTask.score, maxScore = currentTask.maxScore)
                    TextTask(taskContent = taskContent)
                }
            }
            TaskType.RADIO_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    StarRating(score = currentTask.score, maxScore = currentTask.maxScore)
                    RadioTask(taskContent = taskContent)
                }
            }
            TaskType.CHECKBOX_INPUT -> {
                val taskContent: TaskContent = mapper.readValue(currentTask.content)
                Column {
                    StarRating(score = currentTask.score, maxScore = currentTask.maxScore)
                    CheckboxTask(taskContent = taskContent)
                }
            }
            else -> Text(text = "Error")
        }
    }
}