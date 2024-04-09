package eu.fitped.priscilla.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
    val currentLesson by remember {
        mutableIntStateOf(0)
    }
    if (chapterId != null) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> {
                navController.popBackStack()
                navController.navigate("LESSON_TASKS/${(state as DataState.Success<LessonDto>).data.course.id}/${(state as DataState.Success<LessonDto>).data.chapter.id}/${(state as DataState.Success<LessonDto>).data.lessonDetailList.first().lessonId}")
            }
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }
    }
}