package eu.fitped.priscilla.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eu.fitped.priscilla.R
import eu.fitped.priscilla.screen.task.CheckboxTask
import eu.fitped.priscilla.screen.task.CodeTask
import eu.fitped.priscilla.screen.task.DNDTask
import eu.fitped.priscilla.screen.task.DraggableItemsTask
import eu.fitped.priscilla.screen.task.InlineTextTask
import eu.fitped.priscilla.screen.task.RadioTask
import eu.fitped.priscilla.screen.task.TextTask
import eu.fitped.priscilla.model.TaskDto
import eu.fitped.priscilla.model.TaskType
import eu.fitped.priscilla.navigation.NavigationItem
import eu.fitped.priscilla.navigation.Screen
import eu.fitped.priscilla.screen.Loading
import eu.fitped.priscilla.screen.task.TaskNotImplemented
import eu.fitped.priscilla.screen.task.TaskTextView

@Composable
fun TasksNavigationPager(
    modifier: Modifier = Modifier,
    taskList: List<TaskDto>,
) {
    val localNavController = rememberNavController()

    var currentPage by rememberSaveable { mutableIntStateOf(0) }

    // TODO BUG When task is evaluated the star rating in the card should change as well if the new rating is better than the old rating

    val onClick = remember {
        mutableStateOf({})
    }

    val pageCount = taskList.size

    val currentTask = remember(currentPage) {
        taskList.getOrNull(currentPage)
    } ?: return

    val score = remember(currentTask) {
        if (currentTask.maxScore == 0) {
            mutableStateOf(StarRatingData(score = 0, maxScore = 100))
        } else {
            mutableStateOf(StarRatingData(score = currentTask.score, maxScore = currentTask.maxScore))
        }
    }

    println(currentTask.content)
    println(currentPage)

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
                    StarRating(
                        score = score.value.score,
                        maxScore = score.value.maxScore,
                        modifier = Modifier.padding(8.dp)
                    )

                    FloatingActionButton(
                        onClick = {
                            Log.v("TaskCard", "PREVIOUS_BTN_CLICK")
                            if (currentPage > 0) {
                                currentPage -= 1
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
                            if (currentPage < pageCount - 1) {
                                currentPage += 1
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
        ) {
            NavHost(
                navController = localNavController,
                startDestination = NavigationItem.TaskUndefined.route
            ) {
                composable(NavigationItem.TaskUndefined.route) {
                    Loading()
                }
                composable(NavigationItem.TaskNotImplemented.route) {
                    TaskNotImplemented()
                }
                composable(NavigationItem.TaskTextOnly.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) {
                    TaskTextView()
                }
                composable(NavigationItem.TaskTextInput.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    Column {
                        TextTask(
                            taskId = backStackEntry.arguments?.getString("taskId"),
                            onClick = onClick
                        )
                    }
                }
                composable(NavigationItem.TaskRadioInput.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    Column {
                        RadioTask(
                            taskId = backStackEntry.arguments?.getString("taskId"),
                            onClick = onClick
                        )
                    }
                }
                composable(NavigationItem.TaskCheckboxInput.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    Column {
                        CheckboxTask(
                            taskId = backStackEntry.arguments?.getString("taskId"),
                            onClick = onClick
                        )
                    }
                }
                composable(NavigationItem.TaskInlineTextInput.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    InlineTextTask(
                        modifier = Modifier.fillMaxWidth(),
                        taskId = backStackEntry.arguments?.getString("taskId"),
                        onClick = onClick
                    )
                }
                composable(NavigationItem.TaskDND.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    DNDTask(
                        taskId = backStackEntry.arguments?.getString("taskId"),
                        )
                }
                composable(NavigationItem.TaskDraggable.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    DraggableItemsTask(
                        taskId = backStackEntry.arguments?.getString("taskId"),
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                composable(NavigationItem.TaskCodeJava.route,
                    arguments = listOf(
                        navArgument("taskId") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    CodeTask(
                        taskId = backStackEntry.arguments?.getString("taskId"),
                        onClick = onClick,
                    )
                }
            }
        }

        val destination = remember(currentTask) {
            val taskType = TaskType.forId(currentTask.taskTypeId)
            when (taskType) {
                TaskType.TEXT_ONLY -> "${Screen.TASK_TEXT_ONLY.name}/${currentTask.taskId}"
                TaskType.TEXT_INPUT -> "${Screen.TASK_TEXT_INPUT.name}/${currentTask.taskId}"
                TaskType.RADIO_INPUT -> "${Screen.TASK_RADIO_INPUT.name}/${currentTask.taskId}"
                TaskType.CHECKBOX_INPUT -> "${Screen.TASK_CHECKBOX_INPUT.name}/${currentTask.taskId}"
                TaskType.TEXT_INSIDE_CONTENT -> "${Screen.TASK_INLINE_TEXT_INPUT.name}/${currentTask.taskId}"
                TaskType.DND -> "${Screen.TASK_DND.name}/${currentTask.taskId}"
                TaskType.DRAGGABLE -> "${Screen.TASK_DRAGGABLE.name}/${currentTask.taskId}"
                TaskType.CODE -> "${Screen.TASK_CODE_JAVA.name}/${currentTask.taskId}"
                TaskType.CODE_ADVANCED -> "${Screen.TASK_CODE_JAVA.name}/${currentTask.taskId}"
                else -> Screen.TASK_NOT_IMPLEMENTED.name
            }
        }

        LaunchedEffect(destination) {
            Log.d("TasksNavigationPager", "Navigating to $destination")
            localNavController.navigate(destination) {
                popUpTo(localNavController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
}