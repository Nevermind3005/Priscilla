package eu.fitped.priscilla.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.BottomNavigationBar
import eu.fitped.priscilla.viewModel.LoginViewModel
import eu.fitped.priscilla.components.CourseList
import eu.fitped.priscilla.model.CourseDto
import eu.fitped.priscilla.model.CoursesDto
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.DashboardViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun Dashboard(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    ) {
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val state by dashboardViewModel.dataState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> CourseList(
                courseList = (state as DataState.Success<CoursesDto>).data.list,
                navController = navController
            )
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }
    }
}