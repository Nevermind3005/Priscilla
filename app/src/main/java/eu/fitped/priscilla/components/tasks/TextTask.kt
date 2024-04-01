package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.model.TaskContent
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.viewModel.TaskViewModel

@Composable
fun TextTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContent,
    activityType: String,
    taskId: Long,
    taskTypeId: Long
) {
    val taskViewModel: TaskViewModel = hiltViewModel()

    var answer by remember { mutableStateOf(taskContent.answerList.first().answer) }

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
        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text(text = "Answer") }
        )
        Button(onClick = {
            val currentTime = System.currentTimeMillis() / 1000
            val taskAnswer = TaskEvalDto(
                activityType = activityType,
                taskId = taskId,
                taskTypeId = taskTypeId,
                timeLength = currentTime - startTime,
                answerList = listOf(answer)
            )
            taskViewModel.evaluate(taskAnswer)
        }) {
            Text(text = "Evaluate")
        }
    }
}