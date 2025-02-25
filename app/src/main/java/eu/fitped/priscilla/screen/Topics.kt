package eu.fitped.priscilla.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.CourseCategoryList
import eu.fitped.priscilla.model.CourseCategoriesDto
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.TopicsViewModel

@Composable
fun Topics(
    navController: NavHostController
) {
    val topicsViewModel: TopicsViewModel = hiltViewModel()
    val state by topicsViewModel.dataState.collectAsState()

    when (state) {
        is DataState.Loading -> Loading()
        is DataState.Success<*> -> CourseCategoryList(
            courseCategoryList = (state as DataState.Success<CourseCategoriesDto>).data.list,
            navController = navController
        )

        is DataState.Error -> {
            Text("Error: ${(state as DataState.Error).message}")
        }
    }
}