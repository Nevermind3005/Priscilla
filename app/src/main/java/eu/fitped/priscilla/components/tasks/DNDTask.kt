package eu.fitped.priscilla.components.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import eu.fitped.priscilla.components.HTMLTextWithDND
import eu.fitped.priscilla.model.TaskContentDND
import eu.fitped.priscilla.viewModel.TaskViewModel

@Composable
fun DNDTask(
    modifier: Modifier = Modifier,
    taskId: String?
) {
    val taskViewModel: TaskViewModel = hiltViewModel()
    val state by taskViewModel.dataState.collectAsState()

    val mapper = jacksonObjectMapper()

    val taskContent: TaskContentDND = mapper.readValue(taskViewModel.getTask()!!.content)

    Column(
        modifier = modifier
    ) {
        HTMLTextWithDND(html = taskContent.content, fakes = taskContent.fakes)
    }
}