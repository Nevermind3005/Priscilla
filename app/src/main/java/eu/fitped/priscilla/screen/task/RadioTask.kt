package eu.fitped.priscilla.screen.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.components.TaskEvalAlert
import eu.fitped.priscilla.model.TaskContent
import eu.fitped.priscilla.model.TaskEvalDto
import eu.fitped.priscilla.model.TaskType
import eu.fitped.priscilla.viewModel.TaskViewModel

@Composable
fun RadioTask(
    modifier: Modifier = Modifier,
    taskId: String?,
    onClick: MutableState<() -> Unit>
) {
    val taskViewModel: TaskViewModel = hiltViewModel()
    val state by taskViewModel.dataState.collectAsState()

    val mapper = jacksonObjectMapper()

    val taskContent: TaskContent = mapper.readValue(taskViewModel.getTask()!!.content)

    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(taskContent.answerList.first().answer)
    }

    val startTime = System.currentTimeMillis() / 1000

    onClick.value = {
        val currentTime = System.currentTimeMillis() / 1000
        val taskAnswer = TaskEvalDto(
            activityType = "chapter",
            taskId = taskId!!.toLong(),
            taskTypeId = TaskType.RADIO_INPUT.id,
            timeLength = currentTime - startTime,
            answerList = listOf(selectedOption)
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
    }
}