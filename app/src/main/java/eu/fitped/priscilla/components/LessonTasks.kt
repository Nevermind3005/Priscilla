package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import eu.fitped.priscilla.model.TasksDto
import eu.fitped.priscilla.screen.Loading
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.LessonViewModel

@Composable
fun LessonTasks() {
    val lessonViewModel: LessonViewModel = hiltViewModel()
    val state by lessonViewModel.dataState.collectAsState()

    when (state) {
        is DataState.Loading -> Loading()
        is DataState.Success<*> -> {
            TasksNavigationPager(
                modifier = Modifier.fillMaxSize(),
                taskList = (state as DataState.Success<TasksDto>).data.taskList,
            )
        }
        is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
    }
}