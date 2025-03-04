package eu.fitped.priscilla.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.LessonList
import eu.fitped.priscilla.model.LessonDto
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.ChapterDetailViewModel

@Composable
fun ChapterDetail(
    navController: NavHostController,
    chapterId: String?
) {
    val chapterDetailViewModel: ChapterDetailViewModel = hiltViewModel()
    val state by chapterDetailViewModel.dataState.collectAsState()
    if (chapterId != null) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> {
                LessonList(
                    lessonDto = (state as DataState.Success<LessonDto>).data,
                    navController = navController
                )
            }
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }
    }
}