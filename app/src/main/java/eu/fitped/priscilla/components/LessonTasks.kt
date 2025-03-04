package eu.fitped.priscilla.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.model.TaskDto
import eu.fitped.priscilla.model.TasksDto
import eu.fitped.priscilla.screen.Loading
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.LessonViewModel

// TODO refactor
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LessonTasks(
    courseId: String?,
    chapterId: String?,
    lessonId: String?,
    navController: NavHostController
) {
    val lessonViewModel: LessonViewModel = hiltViewModel()
    val state by lessonViewModel.dataState.collectAsState()
    val scroll = rememberScrollState(0)
    val pagerState = rememberPagerState {
        10
    }

    when (state) {
        is DataState.Loading -> Loading()
        is DataState.Success<*> -> {
            TaskCard(
                taskList = (state as DataState.Success<TasksDto>).data.taskList
            ) { taskDtos: List<TaskDto>, pagerState: PagerState, score: MutableState<StarRatingData>, onClick: MutableState<() -> Unit> ->
                TasksPager(
                    modifier = Modifier.fillMaxSize(),
                    taskList = taskDtos,
                    pagerState = pagerState,
                    score = score,
                    onClick
                )
            }
        }
        is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
    }
}

//HTMLText(
//modifier = Modifier.verticalScroll(scroll),
//html = (state as DataState.Success<TasksDto>).data.taskList.first().content
//)

//@Composable
//fun ShowBasicHTML(
//    html: String,
//) {
//    val htmlText = "This is <b>bold</b> and <i>italic</i> text"
//
//    Column {
//        Text("Other composable elements...")
//        AndroidView(
//            factory = { context ->
//                TextView(context).apply {
//                    text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT),
//                }
//            }
//        )
//    }
//}
