package eu.fitped.priscilla.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import eu.fitped.priscilla.R
import eu.fitped.priscilla.model.TaskDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    modifier: Modifier = Modifier,
    onEvaluateButtonClick: () -> Unit = {},
    taskList: List<TaskDto>,
    content: @Composable (List<TaskDto>, PagerState, MutableState<StarRatingData>, MutableState<() -> Unit>) -> Unit,
) {
    val tasksPagerStat = rememberPagerState(pageCount = {
        taskList.size
    })
    val score = remember {
        mutableStateOf(StarRatingData(0 ,100 ))
    }

    val onClick = remember {
        mutableStateOf({})
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onClick.value, modifier = Modifier.padding(8.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.task_alt_24px),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    StarRating(score = score.value.score, maxScore = score.value.maxScore, modifier = Modifier.padding(8.dp))

                    val coroutineScope = rememberCoroutineScope()

                    FloatingActionButton(
                        onClick = {
                            Log.v("TaskCard", "PREVIOUS_BTN_CLICK")
                            if (tasksPagerStat.currentPage > 0) {
                                coroutineScope.launch {
                                    // Call scroll to on pagerState
                                    tasksPagerStat.scrollToPage(tasksPagerStat.currentPage - 1)
                                }
                            } else {
                                {}
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back_ios_24px),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            Log.v("TaskCard", "NEXT_BTN_CLICK")
                            if (tasksPagerStat.pageCount > tasksPagerStat.currentPage) {
                                coroutineScope.launch {
                                    // Call scroll to on pagerState
                                    tasksPagerStat.scrollToPage(tasksPagerStat.currentPage + 1)
                                }
                            } else {
                                {}
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_forward_ios_24px),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding()),
        )
        {
            content(taskList, tasksPagerStat, score, onClick)
        }
    }
}