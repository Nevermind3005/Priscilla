package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
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
fun RadioTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContent,
    activityType: String,
    taskId: Long,
    taskTypeId: Long
) {
    val taskViewModel: TaskViewModel = hiltViewModel()

    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(taskContent.answerList.first().answer )
    }

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
        taskContent.answerList.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (it.answer == selectedOption),
                        onClick = {
                            onOptionSelected(it.answer)
                        }
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (it.answer == selectedOption),
                    onClick = {
                        onOptionSelected(it.answer)
                    },
                )
                Text(
                    text = it.answer,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Button(onClick = {
            val currentTime = System.currentTimeMillis() / 1000
            val taskAnswer = TaskEvalDto(
                activityType = activityType,
                taskId = taskId,
                taskTypeId = taskTypeId,
                timeLength = currentTime - startTime,
                answerList = listOf(selectedOption)
            )
            taskViewModel.evaluate(taskAnswer)
        }) {
            Text(text = "Evaluate")
        }
    }
}