package eu.fitped.priscilla.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.CourseCategoryDto

@Composable
fun CourseCategoryList(
    modifier: Modifier = Modifier,
    courseCategoryList: List<CourseCategoryDto>,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    val isCompactHeader = remember { derivedStateOf { listState.firstVisibleItemScrollOffset > 5 || listState.firstVisibleItemIndex > 0 } }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)),
            ) {
                AnimatedVisibility(visible = !isCompactHeader.value) {
                    FullHeader(text = stringResource(R.string.topics))
                }
                AnimatedVisibility(visible = isCompactHeader.value) {
                    CompactHeader(text = stringResource(R.string.topics))
                }
            }
        }
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
                count = courseCategoryList.size,
                key = {
                    courseCategoryList[it].categoryId
                },
                itemContent = { index ->
                    CourseCategoryCard(
                        courseCategoryDto = courseCategoryList[index]
                    )
                }
            )
        }
    }

}