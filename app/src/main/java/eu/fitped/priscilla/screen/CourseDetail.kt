package eu.fitped.priscilla.screen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.ChapterList
import eu.fitped.priscilla.model.CourseDetailDto
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.CourseDetailViewModel

@Composable
fun CourseDetail(
    navController: NavHostController,
    courseId: String?
) {
    val courseDetailViewModel: CourseDetailViewModel = hiltViewModel()
    val state by courseDetailViewModel.dataState.collectAsState()

    if (courseId != null) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> ChapterList(
                chapterList = (state as DataState.Success<CourseDetailDto>).data.chapterList,
                title = (state as DataState.Success<CourseDetailDto>).data.title,
                navController = navController
            )
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }

    }
}