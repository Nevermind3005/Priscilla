package eu.fitped.priscilla.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.fitped.priscilla.model.ChapterDto

@Composable
fun ChapterList(
    modifier: Modifier = Modifier,
    chapterList: List<ChapterDto>,
    title: String,
    navController: NavHostController
) {
    val listState = rememberLazyListState()

    ScreenHeader(
        listState = listState,
        headerText = title
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
                count = chapterList.size,
                key = {
                    chapterList[it].chapterId
                },
                itemContent = { index ->
                    ChapterCard(
                        chapterDto = chapterList[index],
                        onClick = {
                            navController.navigate("CHAPTER_DETAIL/${chapterList[index].chapterId}")
                        }
                    )
                }
            )
        }
    }
}
