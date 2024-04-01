package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.model.TaskContent
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.viewModel.TaskViewModel

@Composable
fun CheckboxTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContent,
    activityType: String,
    taskId: Long,
    taskTypeId: Long
) {
    val taskViewModel: TaskViewModel = hiltViewModel()

    val checkedItems = remember { mutableStateOf(List(taskContent.answerList.size) { false }) }

    val startTime = System.currentTimeMillis() / 1000

    Column(
        modifier = modifier
    ) {
        HTMLText(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            html = taskContent.content
        )
        taskContent.answerList.forEachIndexed { index, it ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = checkedItems.value[index],
                        onClick = {
                            checkedItems.value = checkedItems.value.toMutableList().also {
                                it[index] = !it[index]
                            }
                        }
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checkedItems.value[index],
                    onCheckedChange = { isChecked ->
                        checkedItems.value = checkedItems.value.toMutableList().also {
                            it[index] = isChecked
                        }
                    }
                )
                Text(
                    text = it.answer,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Button(onClick = {
            val currentTime = System.currentTimeMillis() / 1000
            val answer = checkedItems.value.zip(taskContent.answerList)
                .filter { it.first }
                .map { it.second.answer }
            val taskAnswer = TaskEvalDto(
                activityType = activityType,
                taskId = taskId,
                taskTypeId = taskTypeId,
                timeLength = currentTime - startTime,
                answerList = answer
            )
            taskViewModel.evaluate(taskAnswer)
        }) {
            Text(text = "Evaluate")
        }
    }

}