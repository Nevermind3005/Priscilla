package eu.fitped.priscilla.components

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.model.TasksDto
import eu.fitped.priscilla.screen.Loading
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.LessonViewModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("RestrictedApi", "StateFlowValueCalledInComposition")
@Composable
fun LessonList(
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
        is DataState.Success<*> -> TasksPager(taskList = (state as DataState.Success<TasksDto>).data.taskList)
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
