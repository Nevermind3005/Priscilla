package eu.fitped.priscilla.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.R
import eu.fitped.priscilla.components.CourseCard
import eu.fitped.priscilla.components.core.GenericList
import eu.fitped.priscilla.model.CoursesDto
import eu.fitped.priscilla.navigation.Screen
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.DashboardViewModel

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val state by dashboardViewModel.dataState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> {
                val data = (state as DataState.Success<CoursesDto>).data
                GenericList(
                    headerText = stringResource(R.string.home),
                    items = data.list,
                    itemKey = { it.courseId }
                ) {
                    CourseCard(
                        courseDto = it,
                        onClick = {
                            navController.navigate("${Screen.COURSE_DETAIL.name}/${it.courseId}")
                        }
                    )
                }
            }
            is DataState.Error -> {
                Text("Error: ${(state as DataState.Error).message}")
            }
        }
    }
}