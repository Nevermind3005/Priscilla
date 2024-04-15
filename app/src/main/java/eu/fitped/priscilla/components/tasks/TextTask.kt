package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import eu.fitped.priscilla.R
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.components.TaskEvalAlert
import eu.fitped.priscilla.model.TaskContent
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.viewModel.TaskViewModel

@Composable
fun TextTask(
    modifier: Modifier = Modifier,
    taskContent: TaskContent,
    activityType: String,
    taskId: Long,
    taskTypeId: Long,
    onClick: MutableState<() -> Unit>
) {
    val taskViewModel: TaskViewModel = hiltViewModel()
    val state by taskViewModel.dataState.collectAsState()

    var answer by remember { mutableStateOf(taskContent.answerList.first().answer) }

    val startTime = System.currentTimeMillis() / 1000

    onClick.value = {
        val currentTime = System.currentTimeMillis() / 1000
        val taskAnswer = TaskEvalDto(
            activityType = activityType,
            taskId = taskId,
            taskTypeId = taskTypeId,
            timeLength = currentTime - startTime,
            answerList = listOf(answer)
        )
        taskViewModel.evaluate(taskAnswer)
    }
    TaskEvalAlert(
        state = state,
        resetState = {taskViewModel.resetState()}
    )
    Column(
        modifier = modifier
    ) {
        HTMLText(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            html = taskContent.content
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            value = answer,
            onValueChange = { answer = it },
            label = { Text(text = stringResource(R.string.answer)) }
        )
    }
}