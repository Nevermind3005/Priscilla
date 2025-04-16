package eu.fitped.priscilla.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.R
import eu.fitped.priscilla.components.core.GenericList
import eu.fitped.priscilla.components.core.SimpleCard
import eu.fitped.priscilla.model.CourseCategoriesDto
import eu.fitped.priscilla.navigation.Screen
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
        is DataState.Success<*> -> {
            val data = (state as DataState.Success<CourseCategoriesDto>).data
            GenericList(
                headerText = stringResource(R.string.topics),
                items = data.list,
                itemKey = { it.categoryId }
            ) {
                SimpleCard(
                    cardText = it.title,
                    onClick = {
                        navController.navigate("${Screen.COURSE_CATEGORY_AREAS.name}/${it.categoryId}")
                    }
                )
            }
        }
        is DataState.Error -> {
            Text("Error: ${(state as DataState.Error).message}")
        }
    }
}