package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.LessonDto

@Composable
fun LessonList(
    modifier: Modifier = Modifier,
    lessonDto: LessonDto,
    navController: NavController
) {
    val listState = rememberLazyListState()

    ScreenHeader(
        listState = listState,
        headerText = stringResource(R.string.lessons)
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(padding)
                .fillMaxSize(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(
                count = lessonDto.lessonDetailList.size,
                key = {
                    lessonDto.lessonDetailList[it].lessonId
                },
                itemContent = { index ->
                    LessonCard(
                        lessonDetail = lessonDto.lessonDetailList[index],
                        onClick = {
                            navController.navigate("LESSON_TASKS/${lessonDto.course.id}/${lessonDto.chapter.id}/${lessonDto.lessonDetailList[index].lessonId}")
                        }
                    )
                }
            )
        }
    }
}