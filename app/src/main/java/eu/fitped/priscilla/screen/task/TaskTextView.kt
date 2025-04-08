package eu.fitped.priscilla.screen.task

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import eu.fitped.priscilla.components.HTMLText
import eu.fitped.priscilla.viewModel.TaskViewModel

@Composable
fun TaskTextView() {
    val taskViewModel: TaskViewModel = hiltViewModel()

    HTMLText(html = taskViewModel.getTask()!!.content)
}