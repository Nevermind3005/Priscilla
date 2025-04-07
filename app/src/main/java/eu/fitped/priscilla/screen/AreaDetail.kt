package eu.fitped.priscilla.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.fitped.priscilla.components.core.GenericList
import eu.fitped.priscilla.components.core.SimpleCard
import eu.fitped.priscilla.model.AreaDto
import eu.fitped.priscilla.navigation.Screen
import eu.fitped.priscilla.util.DataState
import eu.fitped.priscilla.viewModel.AreaDetailViewModel

@Composable
fun AreaDetail(
    navController: NavHostController,
    areaId: String?
) {
    val areaDetailViewModel: AreaDetailViewModel = hiltViewModel()
    val state by areaDetailViewModel.dataState.collectAsState()
    if (areaId != null) {
        when (state) {
            is DataState.Loading -> Loading()
            is DataState.Success<*> -> {
                val data = (state as DataState.Success<AreaDto>).data
                GenericList(
                    headerText = data.area.name,
                    items = data.courses,
                    itemKey = { it.id }
                ) {
                    SimpleCard(
                        cardText = it.title,
                        disabled = it.courseStatus == "opened",
                        onClick = {
                            navController.navigate("${Screen.COURSE_PREVIEW.name}/${it.id}")
                        }
                    )
                }
            }
            is DataState.Error -> Text("Error: ${(state as DataState.Error).message}")
        }
    }
}